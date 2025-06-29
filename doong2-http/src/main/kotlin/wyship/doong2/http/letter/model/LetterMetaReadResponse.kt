package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LetterMetaReadResult

data class LetterMetaReadResponse(
    val senderNickname: String?,
    val receiverNickname: String,
    val musics: List<LetterMusicResponse>,
) {
    companion object {
        fun from(result: LetterMetaReadResult): LetterMetaReadResponse = LetterMetaReadResponse(
            senderNickname = result.senderNickname,
            receiverNickname = result.receiverNickname,
            musics = result.musics.map { LetterMusicResponse.from(it) },
        )
    }
}
