package wyship.doong2.core.letter.port

import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.domain.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime

interface LetterQueryPort {
    /**
     * find
     */
    fun findByReceiverIdAndScheduleDate(
        receiverId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<List<Letter>>

    fun findByReceiverIdAndScheduleDate(
        receiverId: Long,
        scheduleDate: LocalDate,
    ): Result<List<Letter>>

    /**
     * count
     */
    fun countByReceiverIdAndViewed(receiverId: Long, viewed: Boolean): Result<Long>

    data class Letter(
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
        val viewed: Boolean,
    )

    class LetterReadFailException : CommonException()
}
