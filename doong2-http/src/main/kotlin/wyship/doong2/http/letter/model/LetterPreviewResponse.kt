package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LetterPreview

data class LetterPreviewResponse(
    val letterId: Long,
    val content: String?,
) {
    companion object {
        fun from(letter: LetterPreview): LetterPreviewResponse = LetterPreviewResponse(
            letterId = letter.letterId,
            content = letter.content,
        )
    }
}
