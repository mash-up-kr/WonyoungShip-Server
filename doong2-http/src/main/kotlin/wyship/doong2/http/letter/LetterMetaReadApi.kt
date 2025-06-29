package wyship.doong2.http.letter

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.LetterMetaReadUseCase
import wyship.doong2.core.letter.model.command.LetterMetaReadCommand
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.LETTER_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.letter.model.LetterMetaReadResponse
import wyship.doong2.http.toApiResponse

@RestController
@RequestMapping(LETTER_URL)
class LetterMetaReadApi(
    private val letterMetaReadUseCase: LetterMetaReadUseCase,
) {

    @Operation(
        summary = "편지 작성 메타 정보 조회",
        description = "편지 작성 시 필요한 수신자 정보, 음악 목록 등을 조회합니다.",
    )
    @GetMapping("/meta")
    fun readLetterMeta(
        @LoginMember memberId: Long?,
        @RequestParam receiverId: Long,
    ): ApiResponse<LetterMetaReadResponse> = letterMetaReadUseCase
        .read(LetterMetaReadCommand(senderId = memberId, receiverId = receiverId))
        .toApiResponse(
            onSuccess = { LetterMetaReadResponse.from(it) },
            onFailure = { throw it },
        )
}
