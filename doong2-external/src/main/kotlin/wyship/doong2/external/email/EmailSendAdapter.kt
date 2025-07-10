package wyship.doong2.external.email

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import wyship.doong2.core.email.AlarmEmail
import wyship.doong2.core.email.port.EmailSendPort

@Component
class EmailSendAdapter(
    private val mailSender: JavaMailSender,
) : EmailSendPort {
    override fun sendAlarmEmails(emails: List<AlarmEmail>): Result<Long> = runCatching {
        emails.count { email ->
            runCatching {
                val message = mailSender.createMimeMessage()
                val helper = MimeMessageHelper(message, false, "UTF-8")
                helper.setTo(email.receiverEmail)
                helper.setSubject(email.title)
                helper.setText(email.content, false)
                mailSender.send(message)
            }.isSuccess
        }.toLong()
    }
}
