package wyship.doong2.core.letter.model.result

import wyship.doong2.core.letter.port.LetterQueryPort.Letter

data class LetterPreview(
    val letterId: Long,
    val content: String?,
) {
    companion object {
        fun from(letter: Letter): LetterPreview = LetterPreview(
            letterId = letter.id,
            content = letter.content.takeIf { letter.viewed },
        )
    }
}
