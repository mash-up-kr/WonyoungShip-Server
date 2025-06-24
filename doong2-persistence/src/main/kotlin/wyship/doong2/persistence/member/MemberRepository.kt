package wyship.doong2.persistence.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): List<Member>

    fun findByTokenId(tokenId: String): Member?

    fun findMemberById(id: Long): Member?
}
