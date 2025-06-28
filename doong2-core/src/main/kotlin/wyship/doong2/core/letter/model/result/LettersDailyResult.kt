package wyship.doong2.core.letter.model.result

import java.time.LocalDate

data class LettersDailyResult(
    val date: LocalDate,
    val letters: List<LetterPreview>,
)
