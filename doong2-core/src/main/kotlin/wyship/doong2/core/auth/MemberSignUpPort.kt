package wyship.doong2.core.auth

interface MemberSignUpPort {
    fun signUp(
        email: String,
        name: String,
    ): MemberId

    data class MemberId(
        val id: Long,
    )
}
