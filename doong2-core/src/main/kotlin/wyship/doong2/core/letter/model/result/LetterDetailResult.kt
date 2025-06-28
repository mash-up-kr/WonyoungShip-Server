package wyship.doong2.core.letter.model.result

import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.port.Letter
import wyship.doong2.core.music.port.MusicQueryPort.Music
import java.time.LocalDate

data class LetterDetailResult(
    val senderNickname: String,
    val marked: Boolean,
    val createdDate: LocalDate,
    val scheduleDate: LocalDate,
    val weatherType: WeatherType,
    val content: String,
    val music: LetterMusic?,
    val fortuneCookieMessage: String?,
) {
    companion object {
        fun from(
            letter: Letter,
            music: Music?,
            fortuneCookieMessage: String?,
        ): LetterDetailResult = LetterDetailResult(
            senderNickname = letter.senderNickname,
            marked = letter.marked,
            createdDate = letter.createdAt.toLocalDate(),
            scheduleDate = letter.scheduleDate,
            weatherType = letter.weatherType,
            content = letter.content,
            music = music?.let { LetterMusic.from(it) },
            fortuneCookieMessage = fortuneCookieMessage,
        )
    }
}
