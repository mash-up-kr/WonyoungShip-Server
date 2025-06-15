package wyship.doong2.core.member

interface MemberExistQueryUseCase {
    fun exist(email: String): Boolean
}
