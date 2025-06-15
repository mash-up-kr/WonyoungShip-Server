package wyship.doong2.core.member

import org.springframework.stereotype.Component
import wyship.doong2.core.member.port.MemberQueryPort

@Component
class MemberExistQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberExistQueryUseCase {
    override fun exist(email: String): Boolean {
        val findMember = memberQueryPort.findMember(email)
        return findMember != null
    }
}
