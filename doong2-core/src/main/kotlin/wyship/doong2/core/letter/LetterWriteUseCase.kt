package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.fortunecookie.port.FortuneCookieQueryPort
import wyship.doong2.core.letter.model.command.LetterWriteCommand
import wyship.doong2.core.letter.model.result.LetterWriteResult
import wyship.doong2.core.letter.port.LetterQueryPort
import wyship.doong2.core.letter.port.LetterSavePort
import wyship.doong2.core.letter.port.LetterUpdatePort

interface LetterWriteUseCase {
    fun write(command: LetterWriteCommand): Result<LetterWriteResult>

    fun markedLetter(memberId: Long, letterId: Long): Result<Boolean>

    sealed class WriteLetterUseCaseException : CommonException()

    class LetterWriteFailException : WriteLetterUseCaseException()
}

@Service
@Transactional
internal class LetterWriteService(
    private val letterSavePort: LetterSavePort,
    private val letterQueryPort: LetterQueryPort,
    private val letterUpdatePort: LetterUpdatePort,
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

    override fun markedLetter(memberId: Long, letterId: Long): Result<Boolean> =
        runCatching {
            val letter = letterQueryPort.findById(letterId).getOrNull()

            if (letter == null || letter.receiverId != memberId) {
                throw LetterWriteUseCase.LetterWriteFailException()
            }

            letter.marked = !letter.marked

            return@runCatching letterUpdatePort
                .updateLetter(LetterUpdatePort.LetterUpdateCommand.from(letter))
                .getOrThrow()
                .marked
        }
}
