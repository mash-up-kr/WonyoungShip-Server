package wyship.doong2.core.music.port

import wyship.doong2.core.exception.CommonException

interface MusicQueryPort {
    fun findById(musicId: Long): Result<ReadMusicResult>

    fun findAll(): Result<List<ReadMusicResult>>

    data class ReadMusicResult(
        val id: Long,
        val title: String,
        val artist: String,
        val url: String,
    )

    class MusicQueryFailException : CommonException()
}
