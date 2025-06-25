package wyship.doong2.persistence.letter

import org.springframework.data.jpa.repository.JpaRepository

interface LetterRepository : JpaRepository<Letter, Long> {
    fun findAllBySenderMemberId(senderMemberId: Long): List<Letter>
    fun findAllByReceiverMemberId(receiverMemberId: Long): List<Letter>
}
