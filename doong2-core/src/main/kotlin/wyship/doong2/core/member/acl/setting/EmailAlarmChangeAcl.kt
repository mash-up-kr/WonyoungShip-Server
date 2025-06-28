package wyship.doong2.core.member.acl.setting

import org.springframework.stereotype.Component
import wyship.doong2.core.member.MemberEmailAlarmChangeUseCase
import wyship.doong2.core.setting.port.EmailAlarmChangePort

@Component
class EmailAlarmChangeAcl(
    private val memberEmailAlarmChangeUseCase: MemberEmailAlarmChangeUseCase,
) : EmailAlarmChangePort {
    override fun changeEmailAlarm(
        memberId: Long,
        isOn: Boolean,
    ): Result<Unit> =
        runCatching {
            if (isOn) {
                memberEmailAlarmChangeUseCase.onEmailAlarm(memberId)
            } else {
                memberEmailAlarmChangeUseCase.offEmailAlarm(memberId)
            }
        }
}
