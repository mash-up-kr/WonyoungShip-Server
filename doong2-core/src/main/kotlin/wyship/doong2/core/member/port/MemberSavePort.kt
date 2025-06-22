package wyship.doong2.core.member.port

import wyship.doong2.core.exception.CommonException

interface MemberSavePort {
    fun saveMember(command: SaveMemberCommand): Result<SaveMemberResult>

    data class SaveMemberCommand(
        val email: String,
        val nickname: String,
        val tokenId: String,
    )

    data class SaveMemberResult(
        val id: Long,
    )

    class MemberSaveFailException : CommonException()
}
