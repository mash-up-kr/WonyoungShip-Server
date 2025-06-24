package wyship.doong2.core.member.port

interface MemberQueryPort {
    fun findMemberByEmailOrNull(email: String): Member?

    fun findMemberByTokenIdOrNull(tokenId: String): Member?

    fun findMemberByIdOrNull(id: Long): Member?

    data class Member(
        val id: Long,
        val name: String,
        val email: String,
        val tokenId: String,
    )
}
