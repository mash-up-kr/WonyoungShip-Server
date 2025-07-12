package wyship.doong2.http.letter.model

import io.swagger.v3.oas.annotations.media.Schema
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.model.command.LetterWriteCommand
import wyship.doong2.core.letter.model.command.LetterWritingType
import java.time.LocalDate

data class LetterWriteRequest(
    val receiverId: Long?,
    val content: String,
    val scheduleDate: LocalDate,
    val weather: WeatherType,
    @Schema(description = "음악 ID (선택)", nullable = true, required = false)
    val musicId: Long?,
    val senderNickname: String,
    val needFortuneCookie: Boolean,
) {
    fun toCommand(userId: Long?, type: LetterWritingType): LetterWriteCommand = LetterWriteCommand(
        senderId = userId,
        receiverId = receiverId,
        content = content,
        scheduleDate = scheduleDate,
        weather = weather,
        musicId = musicId,
        senderNickname = senderNickname,
        needFortuneCookie = needFortuneCookie,
        type = type,
    )
}
