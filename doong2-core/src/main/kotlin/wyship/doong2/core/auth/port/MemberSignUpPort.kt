package wyship.doong2.core.auth.port

interface MemberSignUpPort {
    fun signUp(command: MemberSignUpCommand): MemberSignUpResult

    data class MemberSignUpCommand(
        val email: String,
        val nickname: String,
        val tokenId: String,
    )

    data class MemberSignUpResult(
        val id: Long,
    )
}
