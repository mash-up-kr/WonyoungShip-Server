package wyship.doong2.core.letter.model.result

data class LetterMetaReadResult(
    val senderNickname: String?,
    val receiverNickname: String,
    val musics: List<LetterMusic>,
)
