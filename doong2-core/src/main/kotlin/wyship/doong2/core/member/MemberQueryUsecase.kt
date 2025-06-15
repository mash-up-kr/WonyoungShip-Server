package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.port.MemberQueryPort

interface MemberQueryUseCase {
    fun getByEmail(email: String): MemberResult

    data class MemberResult(
        val id: Long,
        val email: String,
        val name: String,
        val tokenId: String,
    )
}

@Service
internal class MemberQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberQueryUseCase {
    override fun getByEmail(email: String): MemberQueryUseCase.MemberResult {
        val member = memberQueryPort.findMember(email) ?: throw IllegalArgumentException()
        return MemberQueryUseCase.MemberResult(member.id, member.email, member.name, member.tokenId)
    }
}
