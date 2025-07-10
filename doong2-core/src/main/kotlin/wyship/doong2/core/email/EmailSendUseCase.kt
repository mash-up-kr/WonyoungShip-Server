package wyship.doong2.core.email

import org.springframework.stereotype.Service
import wyship.doong2.core.email.port.EmailSendPort
import wyship.doong2.core.letter.port.LetterQueryPort
import wyship.doong2.core.member.port.MemberQueryPort

interface EmailSendUseCase {
    fun send(todayLetterIds: List<Long>): Result<Long>
}

@Service
internal class EmailSendService(
    private val letterQueryPort: LetterQueryPort,
    private val memberQueryPort: MemberQueryPort,
    private val emailSendPort: EmailSendPort,
) : EmailSendUseCase {
    override fun send(todayLetterIds: List<Long>): Result<Long> {
        val scheduledLettersResult = letterQueryPort.findLettersByIds(todayLetterIds)
        val scheduledLetters = scheduledLettersResult.getOrElse { return Result.failure(it) }

        val alarmEmails = AlarmEmailGenerator.generateAll(scheduledLetters) { memberQueryPort.findMemberByIdOrNull(it) }
        return emailSendPort.sendAlarmEmails(alarmEmails)
    }
}
