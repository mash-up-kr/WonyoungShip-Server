package wyship.doong2.core.member

interface MemberSavePort {
    fun saveMember(command: SaveMemberCommand): SaveMemberResult

    data class SaveMemberCommand(
        val email: String,
        val name: String,
        val tokenId: String,
    )

    data class SaveMemberResult(
        val id: Long,
    )
}
