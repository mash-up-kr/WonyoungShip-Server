package wyship.doong2.http.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.LOGOUT_URL
import wyship.doong2.http.config.LoginMember

@Tag(name = "로그아웃 API")
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class LogoutApi {
    @Operation(
        summary = "로그아웃",
        description = "현재 계정을 로그아웃합니다",
    )
    @PostMapping(LOGOUT_URL)
    fun logout(
        @LoginMember id: Long,
    ): ApiResponse<Unit> {
        // val blah blah
        return ApiResponse.success()
    }
}
