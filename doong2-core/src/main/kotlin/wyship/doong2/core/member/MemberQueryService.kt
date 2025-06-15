package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.port.MemberQueryPort

@Service
class MemberQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberQueryUseCase {
    override fun getByEmail(email: String): MemberQueryUseCase.MemberResult {
        val member = memberQueryPort.findMember(email) ?: throw IllegalArgumentException()
        return MemberQueryUseCase.MemberResult(member.id, member.email, member.name, member.tokenId)
    }
}
