package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.model.result.LetterDetailResult
import wyship.doong2.core.letter.model.result.LetterMusic
import java.time.LocalDate

data class LetterDetailResponse(
    val senderNickname: String,
    val marked: Boolean,
    val createdDate: LocalDate,
    val scheduleDate: LocalDate,
    val weatherType: WeatherType,
    val content: String,
    val music: LetterMusicResponse?,
    val fortuneCookieMessage: String?,
) {
    companion object {
        fun from(letter: LetterDetailResult): LetterDetailResponse = LetterDetailResponse(
            senderNickname = letter.senderNickname,
            marked = letter.marked,
            createdDate = letter.createdDate,
            scheduleDate = letter.scheduleDate,
            weatherType = letter.weatherType,
            content = letter.content,
            music = letter.music?.let { LetterMusicResponse.from(it) },
            fortuneCookieMessage = letter.fortuneCookieMessage,
        )
    }
}

data class LetterMusicResponse(
    val id: Long,
    val isRecommend: Boolean,
    val title: String,
    val artist: String,
    val url: String,
    val mood: String,
) {
    companion object {
        fun from(music: LetterMusic): LetterMusicResponse = LetterMusicResponse(
            id = music.id,
            isRecommend = music.isRecommend,
            title = music.title,
            artist = music.artist,
            url = music.url,
            mood = music.mood,
        )
    }
}
