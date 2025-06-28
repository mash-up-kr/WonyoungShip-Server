package wyship.doong2.http.letter

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.LetterWriteUseCase
import wyship.doong2.core.letter.LetterWriteUseCase.LetterWriteFailException
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.model.command.LetterWriteCommand
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LETTER_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.letter.doc.LetterWriteApiSwagger
import wyship.doong2.http.letter.doc.LetterWriteSwagger
import wyship.doong2.http.letter.model.LetterMarkedResponse
import wyship.doong2.http.toApiResponse
import java.time.LocalDate

@LetterWriteApiSwagger
@RestController
@RequestMapping(LETTER_URL)
class LetterWriteApi(
    private val letterWriteUseCase: LetterWriteUseCase,
) {

    @PatchMapping("/marked/{letterId}")
    fun markedLetter(
        @LoginMember memberId: Long,
        @PathVariable letterId: Long,
    ): ApiResponse<LetterMarkedResponse> =
        letterWriteUseCase.markedLetter(memberId, letterId)
            .toApiResponse(
                onSuccess = { LetterMarkedResponse(letterId, it) },
                onFailure = { onFailure(it) },
            )

    @LetterWriteSwagger
    @PostMapping
    fun writeLetter(
        @RequestBody request: LetterWriteRequest,
    ): ApiResponse<LetterWriteResponse> =
        letterWriteUseCase
            .write(request.toCommand())
            .toApiResponse(
                onSuccess = { LetterWriteResponse(it.letterId) },
                onFailure = { onFailure(it) },
            )

    fun <T> onFailure(exception: Throwable): ApiResponse<T> {
        val letterException = exception as? LetterWriteFailException
        return when (letterException) {
            is LetterWriteFailException -> ApiResponse(HttpErrorType.INVALID_WRITE_LETTER)
            null -> ApiResponse(HttpErrorType.INTERNAL_ERROR)
            else -> ApiResponse(HttpErrorType.INTERNAL_ERROR)
        }
    }

    data class LetterWriteRequest(
        val senderId: Long?,
        val receiverId: Long,
        val content: String,
        val scheduleDate: LocalDate,
        val weather: WeatherType,
        val musicId: Long?,
        val senderNickname: String,
        val needFortuneCookie: Boolean,
    ) {
        fun toCommand(): LetterWriteCommand = LetterWriteCommand(
            senderId = senderId,
            receiverId = receiverId,
            content = content,
            scheduleDate = scheduleDate,
            weather = weather,
            musicId = musicId,
            senderNickname = senderNickname,
            needFortuneCookie = needFortuneCookie,
        )
    }

    data class LetterWriteResponse(
        val letterId: Long,
    )
}
