package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.Member
import wyship.doong2.core.member.port.MemberQueryPort

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
        return Member(member.id!!, member.nickname, member.email, member.tokenId, member.emailAlarmAgreed)
    }

    @Transactional
    override fun findMemberByTokenIdOrNull(tokenId: String): Member? =
        memberRepository
            .findByTokenId(tokenId)
            ?.let { Member(it.id!!, it.nickname, it.email, it.tokenId, it.emailAlarmAgreed) }

    @Transactional
    override fun findMemberByIdOrNull(id: Long): Member? =
        memberRepository
            .findMemberById(id)
            ?.let { Member(it.id!!, it.nickname, it.email, it.tokenId, it.emailAlarmAgreed) }

    @Transactional
    override fun findAllMembers(): List<Member> =
        memberRepository.findAll()
            .filter { it.id != null }
            .map { Member(it.id!!, it.nickname, it.email, it.tokenId, it.emailAlarmAgreed) }

    private fun filterSoftDeleted(): (wyship.doong2.persistence.member.MemberEntity) -> Boolean =
        { it.deletedAt == null }
}
