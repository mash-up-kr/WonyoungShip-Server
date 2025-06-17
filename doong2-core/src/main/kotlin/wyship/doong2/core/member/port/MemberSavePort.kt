package wyship.doong2.core.member.port

interface MemberSavePort {
    fun saveMember(command: SaveMemberCommand): SaveMemberResult

    data class SaveMemberCommand(
        val email: String,
        val nickname: String,
        val tokenId: String,
    )

    data class SaveMemberResult(
        val id: Long,
    )
}
