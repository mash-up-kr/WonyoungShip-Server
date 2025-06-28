package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.port.MemberEmailAlarmChangePort

interface MemberEmailAlarmChangeUseCase {
    fun offEmailAlarm(memberId: Long): Result<Unit>

    fun onEmailAlarm(memberId: Long): Result<Unit>
}

@Service
internal class MemberEmailAlarmChangeService(
    private val memberEmailAlarmChangePort: MemberEmailAlarmChangePort,
) : MemberEmailAlarmChangeUseCase {
    override fun offEmailAlarm(memberId: Long): Result<Unit> =
        runCatching {
            memberEmailAlarmChangePort.offEmailAlarm(memberId)
        }

    override fun onEmailAlarm(memberId: Long): Result<Unit> =
        runCatching {
            memberEmailAlarmChangePort.onEmailAlarm(memberId)
        }
}
