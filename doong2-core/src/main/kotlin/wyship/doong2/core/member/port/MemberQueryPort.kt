package wyship.doong2.core.member.port

interface MemberQueryPort {
    fun findMemberOrNull(email: String): Member?

    data class Member(
        val id: Long,
        val name: String,
        val email: String,
        val tokenId: String,
    )
}
