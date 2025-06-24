package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.MemberQueryPort
import wyship.doong2.core.member.port.MemberQueryPort.Member

@Component
class MemberQueryAdapter(
    private val memberRepository: MemberRepository,
) : MemberQueryPort {
    @Transactional
    override fun findMemberByEmailOrNull(email: String): Member? {
        val memberList =
            memberRepository
                .findByEmail(email)
                .filter(filterSoftDeleted())
                .sortedBy { it.createdAt }
        if (memberList.isEmpty()) {
            return null
        }
        val member = memberList[0]
        return Member(member.id!!, member.nickname, member.email, member.tokenId)
    }

    @Transactional
    override fun findMemberByTokenIdOrNull(tokenId: String): Member? =
        memberRepository
            .findByTokenId(tokenId)
            ?.takeIf(filterSoftDeleted())
            ?.let { Member(it.id!!, it.nickname, it.email, it.tokenId) }

    @Transactional
    override fun findMemberByIdOrNull(id: Long): Member? =
        memberRepository
            .findMemberById(id)
            ?.takeIf(filterSoftDeleted())
            ?.let { Member(it.id!!, it.nickname, it.email, it.tokenId) }

    private fun filterSoftDeleted(): (wyship.doong2.persistence.member.Member) -> Boolean = { it.deletedAt != null }
}
