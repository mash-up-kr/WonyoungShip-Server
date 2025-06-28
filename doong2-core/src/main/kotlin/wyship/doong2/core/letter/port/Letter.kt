package wyship.doong2.core.letter.port

import wyship.doong2.core.letter.domain.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime

data class Letter(
    val id: Long,
    val senderId: Long?,
    val senderNickname: String,
    val receiverId: Long,
    val content: String,
    val weatherType: WeatherType,
    val musicId: Long?,
    val scheduleDate: LocalDate,
    val fortuneCookieId: Long?,
    val createdAt: LocalDateTime,
    var viewed: Boolean,
    var marked: Boolean,
)
