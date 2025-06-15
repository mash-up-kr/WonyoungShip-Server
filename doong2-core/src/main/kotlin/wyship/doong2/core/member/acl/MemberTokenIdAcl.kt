package wyship.doong2.core.member.acl

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.port.MemberTokenIdPort
import wyship.doong2.core.member.MemberQueryUseCase

@Component
class MemberTokenIdAcl(
    private val memberQueryUseCase: MemberQueryUseCase,
) : MemberTokenIdPort {
    override fun getTokenId(email: String): String = memberQueryUseCase.getByEmail(email).email
}
