package wyship.doong2.persistence.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Members, Long> {
    fun findByEmail(email: String): Members?

    fun existsByEmail(email: String): Boolean
}
