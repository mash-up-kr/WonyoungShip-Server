package wyship.doong2.core.member

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.member.MemberSignUpUseCase.MemberSignUpCommand
import wyship.doong2.core.member.MemberSignUpUseCase.MemberSignUpResult
import wyship.doong2.core.member.port.MemberQueryPort
import wyship.doong2.core.member.port.MemberSavePort

interface MemberSignUpUseCase {
    fun signUp(command: MemberSignUpCommand): Result<MemberSignUpResult>

    fun isSignUpAvailable(email: String): Boolean

    data class MemberSignUpCommand(
        val email: String,
        val nickname: String,
        val tokenId: String,
    )

    data class MemberSignUpResult(
        val memberId: Long,
    )

    class MemberSignUpNotAvailableException : CommonException()

    class MemberSignUpInternalException : CommonException()
}

@Service
internal class MemberSignUpService(
    private val memberSavePort: MemberSavePort,
    private val memberQueryPort: MemberQueryPort,
) : MemberSignUpUseCase {
    @Transactional
    override fun signUp(command: MemberSignUpCommand): Result<MemberSignUpResult> {
        if (!isSignUpAvailable(command.email)) {
            return Result.failure(MemberSignUpUseCase.MemberSignUpNotAvailableException())
        }

        return memberSavePort
            .saveMember(
                MemberSavePort.SaveMemberCommand(
                    email = command.email,
                    nickname = command.nickname,
                    tokenId = command.tokenId,
                ),
            ).fold(
                onSuccess = { Result.success(MemberSignUpResult(it.id)) },
                onFailure = { _ ->
                    Result.failure(MemberSignUpUseCase.MemberSignUpInternalException())
                },
            )
    }

    override fun isSignUpAvailable(email: String): Boolean {
        val existMember = memberQueryPort.findMemberOrNull(email)
        return existMember == null
    }
}
