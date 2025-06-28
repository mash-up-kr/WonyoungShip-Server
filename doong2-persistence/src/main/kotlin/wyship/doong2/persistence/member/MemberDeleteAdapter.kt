package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.MemberDeletePort
import java.time.LocalDateTime

@Component
class MemberDeleteAdapter(
    private val memberRepository: MemberRepository,
) : MemberDeletePort {
    @Transactional
    override fun delete(memberId: Long): Result<Unit> {
        val member = memberRepository.findMemberById(memberId) ?: return Result.failure(IllegalStateException())
        member.deletedAt = LocalDateTime.now()
        return Result.success(Unit)
    }
}
