package wyship.doong2.persistence.letter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import wyship.doong2.core.letter.port.LetterQueryPort
import java.time.LocalDate

@Component
class LetterQueryAdapter(
    private val letterRepository: LetterRepository,
) : LetterQueryPort {

    override fun findByReceiverIdAndScheduleDate(
        memberId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<List<LetterQueryPort.ReadLetterResult>> =
        runCatching {
            letterRepository.findAllByReceiverMemberIdAndScheduleDateBetween(memberId, startDate, endDate)
                .filter { it.id != null }
                .map {
                    LetterQueryPort.ReadLetterResult(
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
                    )
                }
        }.onFailure {
            log.warn("[LetterReadAdapter][findReceivedLettersByMemberIdAndScheduleDate] failed to read letter: $it")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(LetterQueryPort.LetterReadFailException()) },
        )

    companion object {
        private val log = LoggerFactory.getLogger(LetterQueryAdapter::class.java)
    }
}
