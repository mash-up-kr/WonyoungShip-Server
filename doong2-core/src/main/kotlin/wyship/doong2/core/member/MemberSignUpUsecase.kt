package wyship.doong2.core.member

interface MemberSignUpUsecase {
    fun signUp(command: MemberSignUpCommand): MemberSignUpResult

    data class MemberSignUpCommand(
        val email: String,
        val name: String,
    )

    data class MemberSignUpResult(
        val memberId: Long,
    )
}
