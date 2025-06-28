package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LetterPreview
import java.time.LocalDate

data class LetterPreviewResponse(
    val letterId: Long,
    val content: String?,
    val scheduleDate: LocalDate,
) {
    companion object {
        fun from(letter: LetterPreview): LetterPreviewResponse = LetterPreviewResponse(
            letterId = letter.letterId,
            content = letter.content,
            scheduleDate = letter.scheduleDate,
        )
    }
}
