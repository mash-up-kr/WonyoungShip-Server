package wyship.doong2.core.letter.model.result

import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.port.Letter
import wyship.doong2.core.music.port.MusicQueryPort.Music
import java.time.LocalDate

data class ReadLettersResult(
    val year: Int,
    val month: Int,
    val letters: List<ReadLetterResult>,
)

data class ReadLetterResult(
    val senderNickName: String,
    val createdDate: LocalDate,
    val scheduleDate: LocalDate,
    val weatherType: WeatherType,
    val content: String,
    val music: ReadLetterMusicResult?,
    val fortuneCookieId: Long?,
) {
    companion object {
        fun from(letter: Letter, music: Music?): ReadLetterResult = ReadLetterResult(
            senderNickName = letter.senderNickname,
            createdDate = letter.createdAt.toLocalDate(),
            scheduleDate = letter.scheduleDate,
            weatherType = letter.weatherType,
            content = letter.content,
            music = music?.let { ReadLetterMusicResult.from(it) },
            fortuneCookieId = letter.fortuneCookieId,
        )
    }
}

data class ReadLetterMusicResult(
    val title: String,
    val artist: String,
    val url: String,
) {
    companion object {
        fun from(music: Music): ReadLetterMusicResult = ReadLetterMusicResult(
            title = music.title,
            artist = music.artist,
            url = music.url,
        )
    }
}
