package wyship.doong2.persistence.fortunecookie

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import wyship.doong2.core.letter.domain.WeatherType

interface FortuneCookieRepository : JpaRepository<FortuneCookieEntity, Long> {

    @Query(
        "SELECT f FROM FortuneCookieEntity f " +
            "WHERE f.weatherType = :weatherType " +
            "ORDER BY function('RAND')",
    )
    fun findRandomByWeatherType(
        @Param("weatherType") weatherType: WeatherType,
        pageable: Pageable,
    ): List<FortuneCookieEntity>
}
