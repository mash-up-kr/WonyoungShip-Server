package wyship.doong2.core.auth.port

import wyship.doong2.core.exception.CommonException

interface MemberSignUpPort {
    fun signUp(command: MemberSignUpCommand): Result<MemberSignUpResult>

    fun isAlreadySignUp(email: String): Boolean

    data class MemberSignUpCommand(
        val email: String,
        val nickname: String,
        val tokenId: String,
    )

    data class MemberSignUpResult(
        val id: Long,
    )

    class MemberSignUpFailException : CommonException()
}
