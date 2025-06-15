package wyship.doong2.core.auth.port

interface MemberExistPort {
    fun exist(query: MemberExistQuery): MemberExistResult

    data class MemberExistQuery(
        val email: String,
    )

    data class MemberExistResult(
        val isExist: Boolean,
    )
}
