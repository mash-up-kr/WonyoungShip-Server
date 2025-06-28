package wyship.doong2.core.member.port

interface MemberDeletePort {
    fun delete(memberId: Long): Result<Unit>
}
