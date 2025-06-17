package wyship.doong2.core.auth.port

interface MemberTokenIdPort {
    fun getTokenId(email: String): String
}
