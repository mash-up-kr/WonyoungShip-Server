package wyship.doong2.http.letter

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.WriteLetterUseCase
import wyship.doong2.core.letter.WriteLetterUseCase.LetterWriteFailException
import wyship.doong2.core.letter.WriteLetterUseCase.WriteLetterCommand
import wyship.doong2.core.letter.WriteLetterUseCase.WriteLetterUseCaseException
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LETTER_WRITE_URL
import wyship.doong2.http.letter.doc.LetterWriteHttpAdapterSwagger
import wyship.doong2.http.letter.doc.LetterWriteSwagger
import wyship.doong2.http.toApiResponse
import java.time.LocalDate

@LetterWriteHttpAdapterSwagger
@RestController
class LetterWriteHttpAdapter(
    private val writeLetterUseCase: WriteLetterUseCase,
) {

    @LetterWriteSwagger
    @PostMapping(LETTER_WRITE_URL)
    fun writeLetter(
        @RequestBody request: LetterWriteRequest,
    ): ApiResponse<LetterWriteResponse> =
        writeLetterUseCase
            .write(request.toCommand())
            .toApiResponse(
                onSuccess = { LetterWriteResponse(it.letterId) },
                onFailure = { exception ->
                    if (exception is WriteLetterUseCaseException) {
                        when (exception) {
                            is LetterWriteFailException -> ApiResponse(HttpErrorType.INVALID_WRITE_LETTER)
                        }
                    } else {
                        ApiResponse(HttpErrorType.INTERNAL_ERROR)
                    }
                },
            )

    data class LetterWriteRequest(
        val senderId: Long?,
        val receiverId: Long,
        val content: String,
        val scheduleDate: LocalDate,
        val weather: WeatherType,
        val musicId: Long?,
        val senderNickname: String,
        val fortuneCookieId: Long?,
    ) {
        fun toCommand(): WriteLetterCommand = WriteLetterCommand(
            senderId = senderId,
            receiverId = receiverId,
            content = content,
            scheduleDate = scheduleDate,
            weather = weather,
            musicId = musicId,
            senderNickname = senderNickname,
            fortuneCookieId = fortuneCookieId,
        )
    }

    data class LetterWriteResponse(
        val letterId: Long,
    )
}
