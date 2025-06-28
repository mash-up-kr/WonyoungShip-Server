package wyship.doong2.persistence.letter

import org.springframework.stereotype.Component
import wyship.doong2.core.letter.port.LetterSavePort
import wyship.doong2.core.letter.port.LetterSavePort.SaveLetterCommand
import wyship.doong2.core.letter.port.LetterSavePort.SaveLetterResult

@Component
class LetterSaveAdapter(
    private val letterRepository: LetterRepository,
) : LetterSavePort {

    override fun saveLetter(command: SaveLetterCommand): Result<SaveLetterResult> = runCatching {
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
            )
        )

        return@runCatching SaveLetterResult(letterEntity.id ?: error("letter id is null"))
    }
}
