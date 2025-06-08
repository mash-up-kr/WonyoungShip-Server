package wyship.doong2.core.member.acl

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.MemberSignUpPort
import wyship.doong2.core.member.MemberSignUpUsecase

@Component
class MemberSignUpAcl(
    private val memberSignUpUsecase: MemberSignUpUsecase,
) : MemberSignUpPort {
    override fun signUp(
        email: String,
        name: String,
    ): MemberSignUpPort.MemberId {
        val result = memberSignUpUsecase.signUp(MemberSignUpUsecase.MemberSignUpCommand(email, name))
        return MemberSignUpPort.MemberId(result.memberId)
    }
}
