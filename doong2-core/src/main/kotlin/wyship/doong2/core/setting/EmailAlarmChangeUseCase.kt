package wyship.doong2.core.setting

import org.springframework.stereotype.Service
import wyship.doong2.core.setting.port.EmailAlarmChangePort

interface EmailAlarmChangeUseCase {
    fun changeEmailAlarm(
        memberId: Long,
        isOn: Boolean,
    ): Result<Unit>
}

@Service
internal class EmailAlarmChangeService(
    private val emailAlarmChangePort: EmailAlarmChangePort,
) : EmailAlarmChangeUseCase {
    override fun changeEmailAlarm(
        memberId: Long,
        isOn: Boolean,
    ): Result<Unit> =
        runCatching {
            emailAlarmChangePort.changeEmailAlarm(memberId, isOn)
        }
}
