package wyship.doong2.core.letter.port

import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.domain.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime

interface LetterQueryPort {
    fun findByReceiverIdAndScheduleDate(
        memberId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<List<ReadLetterResult>>

    data class ReadLetterResult(
        val id: Long,
        val senderId: Long?,
        val senderNickname: String,
        val receiverId: Long,
        val content: String,
        val weatherType: WeatherType,
        val musicId: Long?,
        val scheduleDate: LocalDate,
        val fortuneCookieId: Long?,
        val createdAt: LocalDateTime,
    )

    class LetterReadFailException : CommonException()
}
