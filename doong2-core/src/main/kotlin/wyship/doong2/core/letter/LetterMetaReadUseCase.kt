package wyship.doong2.core.letter

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.model.command.LetterMetaReadCommand
import wyship.doong2.core.letter.model.result.LetterMetaReadResult
import wyship.doong2.core.letter.model.result.LetterMusic
import wyship.doong2.core.member.port.MemberQueryPort
import wyship.doong2.core.music.port.MusicQueryPort

interface LetterMetaReadUseCase {
    fun read(command: LetterMetaReadCommand): Result<LetterMetaReadResult>

    sealed class LetterMetaReadUseCaseException : CommonException()

    class LetterMetaReadFailException : LetterMetaReadUseCaseException()
}

@Service
internal class LetterMetaReadService(
    private val memberQueryPort: MemberQueryPort,
    private val musicQueryPort: MusicQueryPort,
) : LetterMetaReadUseCase {

    override fun read(command: LetterMetaReadCommand): Result<LetterMetaReadResult> = runCatching {
        val sender = command.senderId?.let { memberQueryPort.findMemberByIdOrNull(it) }
        val receiver = memberQueryPort.findMemberByIdOrNull(command.receiverId)
            ?: throw LetterMetaReadUseCase.LetterMetaReadFailException()
        val musics = musicQueryPort.findAll().getOrThrow()

        LetterMetaReadResult(
            senderNickname = sender?.name,
            receiverNickname = receiver.name,
            musics = LetterMusic.from(musics),
        )
    }
}
