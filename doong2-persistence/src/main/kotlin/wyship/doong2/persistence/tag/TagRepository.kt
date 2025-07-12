package wyship.doong2.persistence.tag

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<TagEntity, Long> {
    fun findByTag(tag: String): TagEntity?
    fun existsByTag(tag: String): Boolean
    fun findAllByMemberId(memberId: Long): List<TagEntity>
}
