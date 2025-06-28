package wyship.doong2.persistence.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun findByEmail(email: String): List<MemberEntity>

    fun findByTokenId(tokenId: String): MemberEntity?

    fun findMemberById(id: Long): MemberEntity?
}
