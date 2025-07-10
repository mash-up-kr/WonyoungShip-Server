package wyship.doong2.batch.letter

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import wyship.doong2.core.email.EmailSendUseCase
import wyship.doong2.core.letter.port.LetterQueryPort
import java.time.LocalDate

@Component
class EmailSendScheduler(
    private val letterQueryPort: LetterQueryPort,
    private val emailSendUseCase: EmailSendUseCase,
) {
    @Scheduled(cron = "0 0 9 * * *")
    fun sendScheduledLetterEmails() {
        val today = LocalDate.now()
        val scheduledLetters = letterQueryPort.findLettersByScheduleDateBetween(today, today.plusDays(1))
            .getOrElse {
                throw RuntimeException("편지 조회 실패", it)
            }
        val letterIds = scheduledLetters.map { it.id }
        emailSendUseCase.send(letterIds)
    }
}
