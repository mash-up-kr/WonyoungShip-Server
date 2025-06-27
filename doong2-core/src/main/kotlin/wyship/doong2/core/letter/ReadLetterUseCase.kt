package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.ReadLetterUseCase.ReadLettersResult
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.port.LetterQueryPort
import wyship.doong2.core.music.port.MusicQueryPort
import java.time.LocalDate

interface ReadLetterUseCase {
    fun readByScheduleDate(command: ReadLettersCommand): Result<ReadLettersResult>

    data class ReadLettersCommand(
        val memberId: Long, // todo. access token 연동 후 리팩토링
        val year: Int,
        val month: Int,
    )

    data class ReadLettersResult(
        val year: Int,
        val month: Int,
        val letters: List<ReadLetterResult>,
    )

    data class ReadLetterResult(
        val senderNickName: String,
        val createdDate: LocalDate,
        val scheduleDate: LocalDate,
        val weatherType: WeatherType,
        val content: String,
        val music: ReadLetterMusicResult?,
        val fortuneCookieId: Long?,
    )

    data class ReadLetterMusicResult(
        val title: String,
        val artist: String,
        val url: String,
    )

    sealed class ReadLetterUseCaseException : CommonException()

    class LetterReadFailException : ReadLetterUseCaseException()
}

@Service
internal class ReadLetterService(
    private val letterQueryPort: LetterQueryPort,
    private val musicQueryPort: MusicQueryPort,
) : ReadLetterUseCase {

    override fun readByScheduleDate(command: ReadLetterUseCase.ReadLettersCommand): Result<ReadLettersResult> {
        val startDate = LocalDate.of(command.year, command.month, FIRST_DAY_OF_MONTH)
        val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())

        val musicMap = musicQueryPort.findAll()
            .getOrElse { throw it }
            .associateBy { it.id }

        val letters = letterQueryPort.findByReceiverIdAndScheduleDate(command.memberId, startDate, endDate)
            .getOrElse { throw it }
            .sortedBy { it.scheduleDate }
            .map {
                val music = it.musicId?.let { musicId ->
                    musicMap[musicId]?.let { music ->
                        ReadLetterUseCase.ReadLetterMusicResult(
                            title = music.title,
                            artist = music.artist,
                            url = music.url,
                        )
                    }
                }

                ReadLetterUseCase.ReadLetterResult(
                    senderNickName = it.senderNickname,
                    createdDate = it.createdAt.toLocalDate(),
                    scheduleDate = it.scheduleDate,
                    weatherType = it.weatherType,
                    content = it.content,
                    music = music,
                    fortuneCookieId = it.fortuneCookieId,
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
                onFailure = { Result.failure(ReadLetterUseCase.LetterReadFailException()) },
            )
    }

    companion object {
        const val FIRST_DAY_OF_MONTH = 1
    }
}
