package wyship.doong2.core.letter.model.command

import wyship.doong2.core.letter.domain.WeatherType
import java.time.LocalDate

enum class LetterWritingType {
    TARGET, SELF, RANDOM
}

data class LetterWriteCommand(
    val senderId: Long?,
    val receiverId: Long?,
    val content: String,
    val scheduleDate: LocalDate,
    val weather: WeatherType,
    val musicId: Long?,
    val senderNickname: String,
    val needFortuneCookie: Boolean,
    val type: LetterWritingType,
)
