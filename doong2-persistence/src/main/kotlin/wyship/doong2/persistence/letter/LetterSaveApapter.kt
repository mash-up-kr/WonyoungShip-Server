package wyship.doong2.persistence.letter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.letter.port.LetterSavePort
import wyship.doong2.core.letter.port.LetterSavePort.SaveLetterCommand
import wyship.doong2.core.letter.port.LetterSavePort.SaveLetterResult

@Component
class LetterSaveAdapter(
    private val letterRepository: LetterRepository,
) : LetterSavePort {

    @Transactional
    override fun saveLetter(command: SaveLetterCommand): Result<SaveLetterResult> =
        runCatching {
            val letter = letterRepository.save(
                Letter(
                    senderMemberId = command.senderId,
                    receiverMemberId = command.receiverId,
                    messageContent = command.content,
                    scheduleDate = command.scheduleDate,
                    decorationId = command.decorationId,
                ),
            )

            SaveLetterResult(
                letter.id ?: throw IllegalStateException("[LetterSaveAdapter][saveLetter] letter id is null"),
            )
        }.onFailure {
            log.warn("[LetterSaveAdapter][saveLetter] Failed to save letter: $it")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(LetterSavePort.LetterSaveFailException()) },
        )

    companion object {
        private val log = LoggerFactory.getLogger(LetterSaveAdapter::class.java)
    }
}
