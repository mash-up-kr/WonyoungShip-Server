package wyship.doong2.core.auth

interface MemberTokenIdPort {
    fun getTokenId(email: String): String
}
