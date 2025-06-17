package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.member.MemberSignUpUseCase.MemberSignUpCommand
import wyship.doong2.core.member.port.MemberSavePort

interface MemberSignUpUseCase {
    fun signUp(command: MemberSignUpCommand): MemberSignUpResult

    data class MemberSignUpCommand(
        val email: String,
        val nickname: String,
        val tokenId: String,
    )

    data class MemberSignUpResult(
        val memberId: Long,
    )
}

@Service
internal class MemberSignUpService(
    private val memberSavePort: MemberSavePort,
) : MemberSignUpUseCase {
    override fun signUp(command: MemberSignUpCommand): MemberSignUpUseCase.MemberSignUpResult {
        val saveMember =
            memberSavePort.saveMember(
                MemberSavePort.SaveMemberCommand(
                    email = command.email,
                    nickname = command.nickname,
                    tokenId = command.tokenId,
                ),
            )
        return MemberSignUpUseCase.MemberSignUpResult(memberId = saveMember.id)
    }
}
