package wyship.doong2.core.music.port

import wyship.doong2.core.exception.CommonException

interface MusicQueryPort {
    fun findById(musicId: Long): Result<Music>

    fun findAll(): Result<List<Music>>

    data class Music(
        val id: Long,
        val title: String,
        val artist: String,
        val url: String,
        val mood: String,
    )

    class MusicQueryFailException : CommonException()
}
