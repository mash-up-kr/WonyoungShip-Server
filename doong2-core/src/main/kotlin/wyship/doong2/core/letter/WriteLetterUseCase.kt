package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.port.LetterSavePort
import java.time.LocalDate

interface WriteLetterUseCase {
    fun write(command: WriteLetterCommand): Result<WriteLetterResult>

    data class WriteLetterCommand(
        val senderId: Long,
        val receiverId: Long,
        val content: String,
        val scheduleDate: LocalDate,
        val decorationId: Long,
    )

    data class WriteLetterResult(
        val letterId: Long,
    )

    sealed class WriteLetterUseCaseException : CommonException()

    class LetterWriteFailException : WriteLetterUseCaseException()
}

@Service
internal class WriteLetterService(
    private val letterSavePort: LetterSavePort,
) : WriteLetterUseCase {

    override fun write(command: WriteLetterUseCase.WriteLetterCommand): Result<WriteLetterUseCase.WriteLetterResult> =
        letterSavePort.saveLetter(
            LetterSavePort.SaveLetterCommand(
                senderId = command.senderId,
                receiverId = command.receiverId,
                content = command.content,
                scheduleDate = command.scheduleDate,
                decorationId = command.decorationId,
            ),
        ).mapCatching {
            WriteLetterUseCase.WriteLetterResult(letterId = it.letterId)
        }.onFailure {
            throw WriteLetterUseCase.LetterWriteFailException()
        }
}
