package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LettersMonthlyResult
import java.time.LocalDate

data class LettersMonthlyResponse(
    val year: Int,
    val month: Int,
    val letters: List<LetterPreviewResponse>,
    val days: List<LocalDate>,
) {
    companion object {
        fun from(result: LettersMonthlyResult): LettersMonthlyResponse = LettersMonthlyResponse(
            year = result.year,
            month = result.month,
            letters = result.letters.map { LetterPreviewResponse.from(it) }.toList(),
            days = result.days,
        )
    }
}
