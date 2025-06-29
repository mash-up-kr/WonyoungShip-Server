package wyship.doong2.persistence.music

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import wyship.doong2.core.music.port.MusicQueryPort

@Component
class MusicQueryAdapter(
    private val musicRepository: MusicRepository,
) : MusicQueryPort {

    override fun findById(musicId: Long): Result<MusicQueryPort.Music> =
        runCatching {
            musicRepository.findById(musicId)
                .filter { it.id != null }
                .map {
                    MusicQueryPort.Music(
                        id = it.id!!,
                        title = it.title,
                        artist = it.artist,
                        url = it.url,
                        mood = it.mood,
                    )
                }
                .orElseThrow {
                    error("일치하는 musicId '$musicId'이(가) 없음")
                }
        }.onFailure {
            log.warn("[MusicQueryAdapter][findById] failed to find music: $it")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(MusicQueryPort.MusicQueryFailException()) },
        )

    override fun findAll(): Result<List<MusicQueryPort.Music>> =
        runCatching {
            musicRepository.findAll()
                .filter { it.id != null }
                .map {
                    MusicQueryPort.Music(
                        id = it.id!!,
                        title = it.title,
                        artist = it.artist,
                        url = it.url,
                        mood = it.mood,
                    )
                }
        }.onFailure {
            log.warn("[MusicQueryAdapter][findAll] failed to find music: $it")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(MusicQueryPort.MusicQueryFailException()) },
        )

    companion object {
        private val log = LoggerFactory.getLogger(MusicQueryAdapter::class.java)
    }
}
