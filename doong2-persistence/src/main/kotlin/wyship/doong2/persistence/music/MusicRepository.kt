package wyship.doong2.persistence.music

import org.springframework.data.jpa.repository.JpaRepository
import wyship.doong2.persistence.letter.Music

interface MusicRepository : JpaRepository<Music, Long>
