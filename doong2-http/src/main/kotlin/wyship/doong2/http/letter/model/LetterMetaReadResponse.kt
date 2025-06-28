package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LetterMetaReadResult
import wyship.doong2.core.letter.model.result.ReadLetterMusicResult

data class LetterMetaReadResponse(
    private val senderNickname: String?,
    private val receiverNickname: String,
    private val musics: List<ReadLetterMusicResult>,
) {
    companion object {
        fun from(result: LetterMetaReadResult): LetterMetaReadResponse = LetterMetaReadResponse(
            senderNickname = result.senderNickname,
            receiverNickname = result.receiverNickname,
            musics = result.musics,
        )
    }
}
