package wyship.doong2.core.letter.model.result

import wyship.doong2.core.music.port.MusicQueryPort.Music

data class LetterMusic(
    val id: Long,
    var isRecommend: Boolean,
    val title: String,
    val artist: String,
    val url: String,
    val mood: String,
) {
    companion object {
        fun from(music: Music): LetterMusic = LetterMusic(
            id = music.id,
            isRecommend = false,
            title = music.title,
            artist = music.artist,
            url = music.url,
            mood = music.mood,
        )

        fun from(musics: List<Music>): List<LetterMusic> = musics.map { from(it) }
    }
}
