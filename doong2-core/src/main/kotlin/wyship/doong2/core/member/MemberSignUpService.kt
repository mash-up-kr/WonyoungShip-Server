package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.MemberSignUpUsecase.MemberSignUpCommand

@Service
class MemberSignUpService(
    private val memberSavePort: MemberSavePort,
) : MemberSignUpUsecase {
    override fun signUp(command: MemberSignUpCommand): MemberSignUpUsecase.MemberSignUpResult {
        TODO("Not yet implemented")
    }
}
