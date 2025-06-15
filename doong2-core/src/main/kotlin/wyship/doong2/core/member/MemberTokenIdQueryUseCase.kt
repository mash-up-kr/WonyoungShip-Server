package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.port.MemberQueryPort

interface MemberTokenIdQueryUseCase {
    fun getTokenIdByEmail(email: String): String
}

@Service
internal class MemberTokenIdQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberTokenIdQueryUseCase {
    override fun getTokenIdByEmail(email: String): String {
        val member = memberQueryPort.findMember(email) ?: throw IllegalStateException()
        return member.tokenId
    }
}
