package wyship.doong2.core.letter.model.result

import wyship.doong2.core.letter.port.Letter
import java.time.LocalDate

data class LetterPreview(
    val letterId: Long,
    val content: String?,
    val scheduleDate: LocalDate,
    val marked: Boolean,
) {
    companion object {
        fun from(letter: Letter): LetterPreview = LetterPreview(
            letterId = letter.id,
            content = letter.content.takeIf { letter.viewed },
            scheduleDate = letter.scheduleDate,
            marked = letter.marked,
        )
    }
}
