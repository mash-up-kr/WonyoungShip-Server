package wyship.doong2.core.email

data class AlarmEmail(
    val receiverEmail: String,
    val title: String,
    val content: String,
)
