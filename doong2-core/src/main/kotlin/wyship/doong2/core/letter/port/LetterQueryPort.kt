package wyship.doong2.core.letter.port

import java.time.LocalDate

interface LetterQueryPort {
    fun findSentLettersByMemberId(memberId: Long): List<Letter>
    fun findReceivedLettersByMemberId(memberId: Long): List<Letter>

    data class Letter(
        val id: Long,
        val senderId: Long,
        val receiverId: Long,
        val content: String,
        val scheduleDate: LocalDate,
        val decorationId: Long,
    )
}
