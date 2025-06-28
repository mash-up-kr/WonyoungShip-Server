package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.port.LetterSavePort
import java.time.LocalDate

interface LetterWriteUseCase {
    fun write(command: WriteLetterCommand): Result<WriteLetterResult>

    data class WriteLetterCommand(
        val senderId: Long?,
        val receiverId: Long,
        val content: String,
        val scheduleDate: LocalDate,
        val weather: WeatherType,
        val musicId: Long?,
        val senderNickname: String,
        val fortuneCookieId: Long?,
    )

    data class WriteLetterResult(
        val letterId: Long,
    )

    sealed class WriteLetterUseCaseException : CommonException()

    class LetterWriteFailException : WriteLetterUseCaseException()
}

@Service
internal class LetterWriteService(
    private val letterSavePort: LetterSavePort,
) : LetterWriteUseCase {

    override fun write(command: LetterWriteUseCase.WriteLetterCommand): Result<LetterWriteUseCase.WriteLetterResult> {
        val result = letterSavePort.saveLetter(
            LetterSavePort.SaveLetterCommand(
                senderId = command.senderId,
                receiverId = command.receiverId,
                content = command.content,
                scheduleDate = command.scheduleDate,
                weather = command.weather,
                musicId = command.musicId,
                senderNickname = command.senderNickname,
                fortuneCookieId = command.fortuneCookieId,
            ),
        ).getOrElse { throw it }

        return Result
            .success(LetterWriteUseCase.WriteLetterResult(letterId = result.letterId))
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(LetterWriteUseCase.LetterWriteFailException()) },
            )
    }
}
