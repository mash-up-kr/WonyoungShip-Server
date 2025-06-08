package wyship.doong2.core.auth

interface MemberExistPort {
    fun exist(query: MemberExistQuery): MemberExitResult

    data class MemberExistQuery(
        val email: String,
    )

    data class MemberExitResult(
        val isExist: Boolean,
    )
}
