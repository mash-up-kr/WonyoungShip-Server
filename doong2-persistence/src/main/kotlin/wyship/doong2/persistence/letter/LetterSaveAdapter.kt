package wyship.doong2.persistence.letter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import wyship.doong2.core.letter.port.LetterSavePort
import wyship.doong2.core.letter.port.LetterSavePort.SaveLetterCommand
import wyship.doong2.core.letter.port.LetterSavePort.SaveLetterResult

@Component
class LetterSaveAdapter(
    private val letterRepository: LetterRepository,
) : LetterSavePort {

    override fun saveLetter(command: SaveLetterCommand): Result<SaveLetterResult> =
        runCatching {
            val letterEntity = letterRepository.save(
                LetterEntity(
                    senderMemberId = command.senderId,
                    receiverMemberId = command.receiverId,
                    messageContent = command.content,
                    scheduleDate = command.scheduleDate,
                    weather = command.weather,
                    musicId = command.musicId,
                    senderNickname = command.senderNickname,
                    fortuneCookieId = command.fortuneCookieId,
                ),
            )
            SaveLetterResult(letterEntity.id ?: error("letter id is null"))
        }.onFailure {
            log.warn("[LetterSaveAdapter][saveLetter] failed to save letter: $it")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(LetterSavePort.LetterSaveFailException()) },
        )

    companion object {
        private val log = LoggerFactory.getLogger(LetterSaveAdapter::class.java)
    }
}
