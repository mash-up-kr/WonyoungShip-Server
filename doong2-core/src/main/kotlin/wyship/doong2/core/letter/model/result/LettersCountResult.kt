package wyship.doong2.core.letter.model.result

data class LettersCountResult(
    val notViewedCount: Long,
    val receivedCountPerDay: List<Int>,
)
