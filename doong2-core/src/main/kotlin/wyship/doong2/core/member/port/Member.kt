package wyship.doong2.core.member.port

data class Member(
    val id: Long,
    val name: String,
    val email: String,
    val tokenId: String,
    val emailAlarmAgreed: Boolean,
)
