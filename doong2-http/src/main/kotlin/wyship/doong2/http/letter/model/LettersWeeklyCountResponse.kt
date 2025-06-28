package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LettersWeeklyCountResult

data class LettersWeeklyCountResponse(
    val notViewedCount: Long,
    val receivedCountPerDay: List<Int>,
) {
    companion object {
        fun from(result: LettersWeeklyCountResult): LettersWeeklyCountResponse = LettersWeeklyCountResponse(
            notViewedCount = result.notViewedCount,
            receivedCountPerDay = result.receivedCountPerDay,
        )
    }
}
