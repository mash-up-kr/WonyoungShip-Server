package wyship.doong2.core.letter.model.command

data class LettersReadCommand(
    val memberId: Long,
    val year: Int,
    val month: Int,
)
