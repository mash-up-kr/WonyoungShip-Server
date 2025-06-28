package wyship.doong2.persistence.setting

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServicePropertyRepository : JpaRepository<ServicePropertyEntity, Long> {
    fun findByServiceKey(key: String): ServicePropertyEntity
}
