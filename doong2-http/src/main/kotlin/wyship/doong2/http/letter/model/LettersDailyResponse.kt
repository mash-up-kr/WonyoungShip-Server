package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LettersDailyResult
import java.time.LocalDate

data class LettersDailyResponse(
    val date: LocalDate,
    val letters: List<LetterPreviewResponse>,
) {
    companion object {
        fun from(result: LettersDailyResult): LettersDailyResponse = LettersDailyResponse(
            date = result.date,
            letters = result.letters.map { LetterPreviewResponse.from(it) },
        )
    }
}
