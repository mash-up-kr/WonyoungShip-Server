package wyship.doong2.core.member.acl

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.port.MemberSignUpPort
import wyship.doong2.core.auth.port.MemberSignUpPort.MemberSignUpFailException
import wyship.doong2.core.member.MemberSignUpUseCase

@Component
class MemberSignUpAcl(
    private val memberSignUpUseCase: MemberSignUpUseCase,
) : MemberSignUpPort {
    override fun signUp(command: MemberSignUpPort.MemberSignUpCommand): Result<MemberSignUpPort.MemberSignUpResult> =
        memberSignUpUseCase
            .signUp(
                MemberSignUpUseCase.MemberSignUpCommand(command.email, command.nickname, command.tokenId),
            ).fold(
                onSuccess = { result -> Result.success(MemberSignUpPort.MemberSignUpResult(result.memberId)) },
                onFailure = { _ -> Result.failure(MemberSignUpFailException()) },
            )

    override fun isSignUpAvailable(email: String): Boolean = memberSignUpUseCase.isSignUpAvailable(email = email)
}
