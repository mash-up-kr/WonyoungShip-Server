package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.port.MemberQueryPort

interface MemberExistQueryUseCase {
    fun exist(email: String): Boolean
}

@Service
internal class MemberExistQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberExistQueryUseCase {
    override fun exist(email: String): Boolean {
        val findMember = memberQueryPort.findMember(email)
        return findMember != null
    }
}
