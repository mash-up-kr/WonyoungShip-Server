package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.model.result.LettersCountResult

data class LettersWeeklyCountResponse(
    val notViewedCount: Long,
    val receivedCountPerDay: List<Int>,
) {
    companion object {
        fun from(result: LettersCountResult): LettersWeeklyCountResponse = LettersWeeklyCountResponse(
            notViewedCount = result.notViewedCount,
            receivedCountPerDay = result.receivedCountPerDay,
        )
    }
}
