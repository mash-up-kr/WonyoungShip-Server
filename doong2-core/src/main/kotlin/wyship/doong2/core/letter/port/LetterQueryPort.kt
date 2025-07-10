package wyship.doong2.core.letter.port

import wyship.doong2.core.exception.CommonException
import java.time.LocalDate

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

    fun findById(letterId: Long): Result<Letter>

    fun findLettersByScheduleDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<List<Letter>>

    fun findLettersByIds(todayLetterIds: List<Long>): Result<List<Letter>>

    /**
     * count
     */
    fun countByReceiverIdAndViewed(receiverId: Long, viewed: Boolean): Result<Long>

    class LetterReadFailException : CommonException()
}
