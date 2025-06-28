package wyship.doong2.core.letter.model.result

data class LettersWeeklyCountResult(
    val notViewedCount: Long,
    val receivedCountPerDay: List<Int>,
)
