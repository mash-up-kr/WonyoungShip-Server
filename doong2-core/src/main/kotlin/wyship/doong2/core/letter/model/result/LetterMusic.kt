package wyship.doong2.core.letter.model.result

import wyship.doong2.core.music.port.MusicQueryPort.Music

data class LetterMusic(
    val title: String,
    val artist: String,
    val url: String,
) {
    companion object {
        fun from(music: Music): LetterMusic = LetterMusic(
            title = music.title,
            artist = music.artist,
            url = music.url,
        )
    }
}
