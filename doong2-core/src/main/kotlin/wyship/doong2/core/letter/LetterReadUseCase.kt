package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.model.command.LettersReadCommand
import wyship.doong2.core.letter.model.result.LettersCountResult
import wyship.doong2.core.letter.model.result.ReadLetterResult
import wyship.doong2.core.letter.model.result.ReadLettersResult
import wyship.doong2.core.letter.port.LetterQueryPort
import wyship.doong2.core.music.port.MusicQueryPort
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

interface LetterReadUseCase {
    fun readByScheduleDate(command: LettersReadCommand): Result<ReadLettersResult>

    fun countWeeklyReceivedLetters(memberId: Long): Result<LettersCountResult>

    sealed class LetterReadUseCaseException : CommonException()

    class LetterFailExceptionRead : LetterReadUseCaseException()
}

@Service
internal class LetterReadService(
    private val letterQueryPort: LetterQueryPort,
    private val musicQueryPort: MusicQueryPort,
) : LetterReadUseCase {

    override fun readByScheduleDate(command: LettersReadCommand): Result<ReadLettersResult> {
        val startDate = LocalDate.of(command.year, command.month, FIRST_DAY_OF_MONTH)
        val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())

        val musicMap = musicQueryPort.findAll()
            .getOrElse { throw it }
            .associateBy { it.id }

        val letters = letterQueryPort.findByReceiverIdAndScheduleDate(command.memberId, startDate, endDate)
            .getOrElse { throw it }
            .sortedBy { it.scheduleDate }
            .map { letter ->
                ReadLetterResult.from(
                    letter,
                    letter.musicId?.let { id -> musicMap[id] },
                )
            }

        return Result
            .success(
                ReadLettersResult(
                    year = command.year,
                    month = command.month,
                    letters = letters,
                ),
            )
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(LetterReadUseCase.LetterFailExceptionRead()) },
            )
    }

    override fun countWeeklyReceivedLetters(memberId: Long): Result<LettersCountResult> =
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

            return@runCatching LettersCountResult(
                notViewedCount = notViewedCount,
                receivedCountPerDay = receivedCountPerDay,
            )
        }

    companion object {
        const val FIRST_DAY_OF_MONTH = 1
    }
}
