package wyship.doong2.core.member

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.MemberDeletePort

interface MemberWithdrawUseCase {
    fun withdraw(memberId: Long): Result<Unit>
}

@Service
internal class MemberWithdrawService(
    private val memberDeletePort: MemberDeletePort,
) : MemberWithdrawUseCase {
    @Transactional
    override fun withdraw(memberId: Long): Result<Unit> =
        memberDeletePort
            .delete(memberId = memberId)
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(IllegalStateException()) }, // TODO
            )
}
