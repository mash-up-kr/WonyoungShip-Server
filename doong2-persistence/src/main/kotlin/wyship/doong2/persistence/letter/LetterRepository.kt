package wyship.doong2.persistence.letter

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface LetterRepository : JpaRepository<LetterEntity, Long> {

    /**
     * find
     */
    fun findAllByReceiverMemberIdAndScheduleDateBetween(
        receiverMemberId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<LetterEntity>

    fun findByReceiverMemberIdAndScheduleDate(receiverId: Long, scheduleDate: LocalDate): List<LetterEntity>

    /**
     * count
     */
    fun countByReceiverMemberIdAndViewed(receiverMemberId: Long, viewed: Boolean): Long
}
