package wyship.doong2.core.member.acl.setting

import org.springframework.stereotype.Component
import wyship.doong2.core.member.MemberWithdrawUseCase
import wyship.doong2.core.setting.port.MemberWithdrawPort

@Component
class MemberWithdrawAcl(
    private val memberWithdrawUseCase: MemberWithdrawUseCase,
) : MemberWithdrawPort {
    override fun withdraw(memberId: Long): Result<Unit> =
        memberWithdrawUseCase
            .withdraw(memberId = memberId)
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(IllegalStateException()) }, // TODO
            )
}
