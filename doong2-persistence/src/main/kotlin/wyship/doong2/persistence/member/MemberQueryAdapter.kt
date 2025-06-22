package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.MemberQueryPort

@Component
class MemberQueryAdapter(
    private val memberRepository: MemberRepository,
) : MemberQueryPort {
    @Transactional
    override fun findMemberOrNull(email: String): MemberQueryPort.Member? {
        val member = memberRepository.findByEmail(email) ?: return null
        return MemberQueryPort.Member(member.id!!, member.nickname, member.email, member.tokenId)
    }
}
