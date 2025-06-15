package wyship.doong2.core.member

interface MemberTokenIdQueryUseCase {
    fun getTokenIdByEmail(email: String): String
}
