package wyship.doong2.persistence.music

import org.springframework.data.jpa.repository.JpaRepository

interface MusicRepository : JpaRepository<MusicEntity, Long>
