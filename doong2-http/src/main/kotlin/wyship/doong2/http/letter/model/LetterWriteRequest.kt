package wyship.doong2.http.letter.model

import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.model.command.LetterWriteCommand
import java.time.LocalDate

data class LetterWriteRequest(
    val receiverId: Long,
    val content: String,
    val scheduleDate: LocalDate,
    val weather: WeatherType,
    val musicId: Long?,
    val senderNickname: String,
    val needFortuneCookie: Boolean,
) {
    fun toCommand(userId: Long?): LetterWriteCommand = LetterWriteCommand(
        senderId = userId,
        receiverId = receiverId,
        content = content,
        scheduleDate = scheduleDate,
        weather = weather,
        musicId = musicId,
        senderNickname = senderNickname,
        needFortuneCookie = needFortuneCookie,
    )
}
