package wyship.doong2.http.letter

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.LetterWriteUseCase
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LETTER_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.letter.doc.LetterWriteApiSwagger
import wyship.doong2.http.letter.doc.LetterWriteSwagger
import wyship.doong2.http.letter.model.LetterMarkedResponse
import wyship.doong2.http.letter.model.LetterWriteRequest
import wyship.doong2.http.letter.model.LetterWriteResponse
import wyship.doong2.http.toApiResponse

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
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) },
            )

    @LetterWriteSwagger
    @PostMapping
    fun writeLetter(
        @LoginMember userId: Long?,
        @RequestBody request: LetterWriteRequest,
    ): ApiResponse<LetterWriteResponse> =
        letterWriteUseCase
            .write(request.toCommand(userId))
            .toApiResponse(
                onSuccess = { LetterWriteResponse(it.letterId) },
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) },
            )
}
