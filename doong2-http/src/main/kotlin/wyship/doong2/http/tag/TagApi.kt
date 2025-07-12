package wyship.doong2.http.tag

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.tag.TagRegisterUseCase
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.tag.model.RegisterTagRequest
import wyship.doong2.http.tag.model.TagResponse
import io.swagger.v3.oas.annotations.tags.Tag as SwaggerTag

@SwaggerTag(name = "태그 API")
@RequestMapping("/tag", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class TagApi(
    private val tagRegisterUseCase: TagRegisterUseCase,
) {
    @Operation(
        summary = "태그 정보 조회",
        description = "고유 태그 문자열로 등록된 정보를 조회합니다 (아직 등록된 계정이 없으면 memberId는 null)",
    )
    @GetMapping("/{tag}")
    fun getTag(
        @PathVariable tag: String,
    ): ApiResponse<TagResponse> =
        runCatching {
            val tagObj = tagRegisterUseCase.findByTag(tag)
            ApiResponse.success(TagResponse(tagObj.id, tagObj.tag, tagObj.memberId))
        }.getOrElse {
            ApiResponse(HttpErrorType.INTERNAL_ERROR)
        }

    @Operation(
        summary = "태그 등록",
        description = "고유 태그 문자열에 계정을 등록합니다",
    )
    @PostMapping("/{tag}")
    fun registerTag(
        @PathVariable tag: String,
        @RequestBody request: RegisterTagRequest,
    ): ApiResponse<TagResponse> =
        runCatching {
            val tagObj = tagRegisterUseCase.registerTag(tag, request.memberId)
            ApiResponse.success(TagResponse(tagObj.id, tagObj.tag, tagObj.memberId))
        }.getOrElse {
            ApiResponse(HttpErrorType.INTERNAL_ERROR)
        }
}
