package wyship.doong2.core.email.port

import wyship.doong2.core.email.AlarmEmail
import wyship.doong2.core.exception.CommonException

interface EmailSendPort {
    fun sendAlarmEmails(emails: List<AlarmEmail>): Result<Long>

    class EmailSendFailException : CommonException()
}
