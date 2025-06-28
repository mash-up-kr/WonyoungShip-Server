package wyship.doong2.core.setting

import org.springframework.stereotype.Service
import wyship.doong2.core.setting.port.MemberWithdrawPort

interface WithdrawUseCase {
    fun withdraw(memberId: Long): Result<Unit>
}

@Service
internal class WithdrawService(
    private val memberWithdrawPort: MemberWithdrawPort,
) : WithdrawUseCase {
    override fun withdraw(memberId: Long): Result<Unit> =
        memberWithdrawPort
            .withdraw(memberId)
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(IllegalStateException()) },
            )
}
