package wyship.doong2.http.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.LOGOUT_URL
import wyship.doong2.http.config.LoginMember

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
