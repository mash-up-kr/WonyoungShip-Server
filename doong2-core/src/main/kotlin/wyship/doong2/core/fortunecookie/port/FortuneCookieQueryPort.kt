package wyship.doong2.core.fortunecookie.port

import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.letter.domain.WeatherType

interface FortuneCookieQueryPort {
    fun findRandomByWeatherType(weatherType: WeatherType): Result<FortuneCookie>

    fun findById(fortuneCookieId: Long): Result<FortuneCookie>

    data class FortuneCookie(
        val id: Long,
        val weatherType: WeatherType,
        val text: String
    )

    class FortuneCookieQueryException: CommonException()
}
