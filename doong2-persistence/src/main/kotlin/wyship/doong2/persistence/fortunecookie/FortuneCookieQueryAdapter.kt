package wyship.doong2.persistence.fortunecookie

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import wyship.doong2.core.fortunecookie.port.FortuneCookieQueryPort
import wyship.doong2.core.fortunecookie.port.FortuneCookieQueryPort.FortuneCookie
import wyship.doong2.core.fortunecookie.port.FortuneCookieQueryPort.FortuneCookieQueryException
import wyship.doong2.core.letter.domain.WeatherType

@Component
class FortuneCookieQueryAdapter(
    private val fortuneCookieRepository: FortuneCookieRepository,
) : FortuneCookieQueryPort {
    override fun findRandomByWeatherType(weatherType: WeatherType): Result<FortuneCookie> = runCatching {
        val result = fortuneCookieRepository
            .findRandomByWeatherType(weatherType, PageRequest.of(0, 1))
            .firstOrNull()
            ?: throw FortuneCookieQueryException()

        return@runCatching FortuneCookie(
            id = result.id!!,
            text = result.text,
            weatherType = result.weatherType,
        )
    }

    override fun findById(fortuneCookieId: Long): Result<FortuneCookie> = runCatching {
        val entity = fortuneCookieRepository.findById(fortuneCookieId)
            .orElseThrow { error("일치하는 fortuneCookieId '$fortuneCookieId'이(가) 없음") }

        return@runCatching FortuneCookie(
            id = entity.id!!,
            text = entity.text,
            weatherType = entity.weatherType,
        )
    }
}
