package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.fortunecookie.port.FortuneCookieQueryPort
import wyship.doong2.core.letter.model.command.LetterWriteCommand
import wyship.doong2.core.letter.model.result.LetterWriteResult
import wyship.doong2.core.letter.port.LetterSavePort

interface LetterWriteUseCase {
    fun write(command: LetterWriteCommand): Result<LetterWriteResult>

    sealed class WriteLetterUseCaseException : CommonException()

    class LetterWriteFailException : WriteLetterUseCaseException()
}

@Service
internal class LetterWriteService(
    private val letterSavePort: LetterSavePort,
    private val fortuneCookieQueryPort: FortuneCookieQueryPort,
) : LetterWriteUseCase {

    override fun write(command: LetterWriteCommand): Result<LetterWriteResult> = runCatching {
        val fortuneCookieId = command.takeIf { it.needFortuneCookie }
            ?.let { fortuneCookieQueryPort.findRandomByWeatherType(it.weather).getOrNull()?.id }

        val result = letterSavePort.saveLetter(
            LetterSavePort.SaveLetterCommand(
                senderId = command.senderId,
                receiverId = command.receiverId,
                content = command.content,
                scheduleDate = command.scheduleDate,
                weather = command.weather,
                musicId = command.musicId,
                senderNickname = command.senderNickname,
                fortuneCookieId = fortuneCookieId,
            ),
        ).getOrElse { throw it }

        return@runCatching LetterWriteResult(letterId = result.letterId)
    }
}
