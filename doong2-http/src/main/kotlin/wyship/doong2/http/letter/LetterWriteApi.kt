package wyship.doong2.http.letter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.LetterWriteUseCase
import wyship.doong2.core.letter.model.command.LetterWritingType
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LETTER_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.letter.model.LetterMarkedResponse
import wyship.doong2.http.letter.model.LetterWriteRequest
import wyship.doong2.http.letter.model.LetterWriteResponse
import wyship.doong2.http.toApiResponse

@Tag(name = "편지 쓰기 API")
@RestController
@RequestMapping(LETTER_URL, produces = [MediaType.APPLICATION_JSON_VALUE])
class LetterWriteApi(
    private val letterWriteUseCase: LetterWriteUseCase,
) {
    @Operation(
        summary = "편지 즐겨찾기 API",
        description = "특정 편지를 즐겨찾기 합니다",
    )
    @PatchMapping("/marked/{letterId}")
    fun markedLetter(
        @LoginMember memberId: Long,
        @PathVariable letterId: Long,
    ): ApiResponse<LetterMarkedResponse> =
        letterWriteUseCase
            .markedLetter(memberId, letterId)
            .toApiResponse(
                onSuccess = { LetterMarkedResponse(letterId, it) },
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) },
            )

    @Operation(
        summary = "편지 작성 API",
        description = "보내는 사람, 받는 사람, 메시지, 예약 날짜, 날씨, 음악, 닉네임, 포춘쿠키를 바디로, 타입(TARGET/SELF/RANDOM)은 쿼리스트링으로 받습니다.",
    )
    @PostMapping
    fun writeLetter(
        @LoginMember userId: Long?,
        @RequestParam type: LetterWritingType,
        @RequestBody request: LetterWriteRequest,
    ): ApiResponse<LetterWriteResponse> =
        letterWriteUseCase
            .write(request.toCommand(userId, type))
            .toApiResponse(
                onSuccess = { LetterWriteResponse(it.letterId) },
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) },
            )
}
