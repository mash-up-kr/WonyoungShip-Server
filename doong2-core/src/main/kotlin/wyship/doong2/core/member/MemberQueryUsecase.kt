package wyship.doong2.core.member

interface MemberQueryUseCase {
    fun getByEmail(email: String): MemberResult

    data class MemberResult(
        val id: Long,
        val email: String,
        val name: String,
        val tokenId: String,
    )
}
