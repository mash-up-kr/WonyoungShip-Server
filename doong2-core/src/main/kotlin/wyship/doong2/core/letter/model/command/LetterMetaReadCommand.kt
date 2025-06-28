package wyship.doong2.core.letter.model.command

data class LetterMetaReadCommand(
    val senderId: Long?,
    val receiverId: Long,
)
