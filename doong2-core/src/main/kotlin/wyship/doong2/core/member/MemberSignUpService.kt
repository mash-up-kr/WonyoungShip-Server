package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.MemberSignUpUseCase.MemberSignUpCommand

@Service
class MemberSignUpService(
    private val memberSavePort: MemberSavePort,
) : MemberSignUpUseCase {
    override fun signUp(command: MemberSignUpCommand): MemberSignUpUseCase.MemberSignUpResult {
        val saveMember =
            memberSavePort.saveMember(
                MemberSavePort.SaveMemberCommand(command.email, command.nickname, command.tokenId),
            )
        return MemberSignUpUseCase.MemberSignUpResult(memberId = saveMember.id)
    }
}
