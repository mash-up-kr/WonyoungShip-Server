package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.fortunecookie.port.FortuneCookieQueryPort
import wyship.doong2.core.letter.model.result.LetterDetailResult
import wyship.doong2.core.letter.model.result.LetterPreview
import wyship.doong2.core.letter.model.result.LettersDailyResult
import wyship.doong2.core.letter.model.result.LettersMonthlyResult
import wyship.doong2.core.letter.model.result.LettersWeeklyCountResult
import wyship.doong2.core.letter.port.LetterQueryPort
import wyship.doong2.core.letter.port.LetterUpdatePort
import wyship.doong2.core.letter.port.LetterUpdatePort.LetterUpdateCommand
import wyship.doong2.core.music.port.MusicQueryPort
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

interface LetterReadUseCase {
    fun readMonthlyReceivedLetters(memberId: Long, year: Int, month: Int): Result<LettersMonthlyResult>

    fun countWeeklyReceivedLetters(memberId: Long): Result<LettersWeeklyCountResult>

    fun readDailyReceivedLetters(memberId: Long, date: LocalDate): Result<LettersDailyResult>

    fun readDetailLetter(memberId: Long, letterId: Long): Result<LetterDetailResult>

    sealed class LetterReadUseCaseException : CommonException()

    class LetterFailExceptionRead : LetterReadUseCaseException()
}

@Service
@Transactional(readOnly = true)
internal class LetterReadService(
    private val letterQueryPort: LetterQueryPort,
    private val letterUpdatePort: LetterUpdatePort,
    private val musicQueryPort: MusicQueryPort,
    private val fortuneCookieQueryPort: FortuneCookieQueryPort,
) : LetterReadUseCase {

    override fun readMonthlyReceivedLetters(memberId: Long, year: Int, month: Int): Result<LettersMonthlyResult> {
        val firstMonthDate = LocalDate.of(year, month, FIRST_DAY_OF_MONTH)
        val startDate = firstMonthDate.minusDays(7)
        val today = LocalDate.now()
        val endDate = firstMonthDate.withDayOfMonth(firstMonthDate.lengthOfMonth() + 7)

        val letters = letterQueryPort.findByReceiverIdAndScheduleDate(memberId, startDate, endDate)
            .getOrElse { throw it }

        val validLetters = letters
            .map { LetterPreview.from(it) }
            .filter { it.scheduleDate >= firstMonthDate && it.scheduleDate <= today }
            .toList()

        return Result
            .success(
                LettersMonthlyResult(
                    year = year,
                    month = month,
                    letters = validLetters,
                    days = letters.map { it.scheduleDate }.distinct().toList(),
                ),
            )
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(LetterReadUseCase.LetterFailExceptionRead()) },
            )
    }

    override fun countWeeklyReceivedLetters(memberId: Long): Result<LettersWeeklyCountResult> =
        runCatching {
            val today = LocalDate.now()
            val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

            val lettersByDateMap = letterQueryPort.findByReceiverIdAndScheduleDate(memberId, monday, sunday)
                .getOrThrow()
                .groupBy { it.scheduleDate }

            val receivedCountPerDay = (0..6).map {
                val date = monday.plusDays(it.toLong())
                lettersByDateMap[date]?.size ?: 0
            }

            val notViewedCount = letterQueryPort.countByReceiverIdAndViewed(memberId, false).getOrThrow()

            return@runCatching LettersWeeklyCountResult(
                notViewedCount = notViewedCount,
                receivedCountPerDay = receivedCountPerDay,
            )
        }

    override fun readDailyReceivedLetters(memberId: Long, date: LocalDate): Result<LettersDailyResult> =
        runCatching {
            val letters = letterQueryPort.findByReceiverIdAndScheduleDate(memberId, date)
                .getOrThrow()
                .map { LetterPreview.from(it) }
                .toList()

            return@runCatching LettersDailyResult(date, letters)
        }

    @Transactional
    override fun readDetailLetter(memberId: Long, letterId: Long): Result<LetterDetailResult> =
        runCatching {
            val letter = letterQueryPort.findById(letterId).getOrThrow()

            if (letter.scheduleDate.isAfter(LocalDate.now()) || letter.receiverId != memberId) {
                throw LetterQueryPort.LetterReadFailException()
            }

            letter.viewed = true
            val updatedLetter = letterUpdatePort.updateLetter(LetterUpdateCommand.from(letter)).getOrThrow()

            val music = updatedLetter.musicId?.let { musicQueryPort.findById(it).getOrThrow() }

            val fortuneCookieMessage = updatedLetter.fortuneCookieId
                ?.let { fortuneCookieQueryPort.findById(it) }
                ?.getOrDefault(null)
                ?.text

            return@runCatching LetterDetailResult.from(updatedLetter, music, fortuneCookieMessage)
        }

    companion object {
        const val FIRST_DAY_OF_MONTH = 1
    }
}
