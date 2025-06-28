package wyship.doong2.persistence.letter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import wyship.doong2.core.letter.port.Letter
import wyship.doong2.core.letter.port.LetterQueryPort
import java.time.LocalDate

@Component
class LetterQueryAdapter(
    private val letterRepository: LetterRepository,
) : LetterQueryPort {

    override fun findByReceiverIdAndScheduleDate(
        receiverId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<List<Letter>> =
        runCatching {
            letterRepository.findAllByReceiverMemberIdAndScheduleDateBetween(receiverId, startDate, endDate)
                .filter { it.id != null }
                .map {
                    Letter(
                        id = it.id!!,
                        senderId = it.senderMemberId,
                        senderNickname = it.senderNickname,
                        receiverId = it.receiverMemberId,
                        content = it.messageContent,
                        weatherType = it.weather,
                        musicId = it.musicId,
                        scheduleDate = it.scheduleDate,
                        fortuneCookieId = it.fortuneCookieId,
                        createdAt = it.createdAt,
                        viewed = it.viewed,
                        marked = it.marked,
                    )
                }
        }.onFailure {
            log.warn("[LetterReadAdapter][findReceivedLettersByMemberIdAndScheduleDate] failed to read letter: $it")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(LetterQueryPort.LetterReadFailException()) },
        )

    override fun findByReceiverIdAndScheduleDate(
        receiverId: Long,
        scheduleDate: LocalDate,
    ): Result<List<Letter>> =
        runCatching {
            return@runCatching letterRepository.findByReceiverMemberIdAndScheduleDate(receiverId, scheduleDate)
                .mapNotNull { it.toDomain() }
        }

    override fun findById(letterId: Long): Result<Letter> = runCatching {
        letterRepository.findById(letterId).map {
            it.toDomain()
                ?: throw IllegalStateException("[LetterQueryAdapter][findById] failed to find letterId = $letterId")
        }.orElseThrow { error("일치하는 letter 없음. id=$letterId") }
    }

    override fun countByReceiverIdAndViewed(receiverId: Long, viewed: Boolean): Result<Long> =
        runCatching {
            return@runCatching letterRepository.countByReceiverMemberIdAndViewed(receiverId, viewed)
        }

    companion object {
        private val log = LoggerFactory.getLogger(LetterQueryAdapter::class.java)
    }
}
