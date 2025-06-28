package wyship.doong2.core.letter.model.result

import java.time.LocalDate

data class LettersMonthlyResult (
    val year: Int,
    val month: Int,
    val letters: List<LetterPreview>,
    val days: List<LocalDate>,
)
