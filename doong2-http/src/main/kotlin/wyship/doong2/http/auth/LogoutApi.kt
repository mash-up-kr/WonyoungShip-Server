package wyship.doong2.http.auth

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.LOGOUT_URL
import wyship.doong2.http.config.LoginMember

@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class LogoutApi {
    @PostMapping(LOGOUT_URL)
    fun logout(
        @LoginMember id: Long,
    ): ApiResponse<Unit> {
        // val blah blah
        return ApiResponse.success()
    }
}
