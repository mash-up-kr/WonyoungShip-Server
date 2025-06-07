package wyship.doong2.http.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.auth.KakaoLoginUsecase
import wyship.doong2.core.auth.KakaoLoginUsecase.KakaoLoginCommand
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LOGIN_URL
import wyship.doong2.http.toApiResponse

@RestController
@RequestMapping
class KakaoLoginHttpAdapter(
    private val kakaoLoginUsecase: KakaoLoginUsecase,
) {
    @PostMapping(LOGIN_URL)
    fun kakaoLogin(
        @RequestBody request: KakaoLoginRequest,
    ): ApiResponse<KakaoLoginResponse> =
        kakaoLoginUsecase
            .login(request.toCommand())
            .toApiResponse(
                {
                    KakaoLoginResponse(it.accessToken, it.refreshToken)
                },
                { errorType ->
                    when (errorType) {
                        else -> ApiResponse(HttpErrorType.INVALID_KAKAO_LOGIN_TOKEN)
                    }
                },
            )

    data class KakaoLoginRequest(
        val token: String,
    ) {
        fun toCommand(): KakaoLoginCommand = KakaoLoginCommand(token)
    }

    data class KakaoLoginResponse(
        val accessToken: String,
        val refreshToken: String,
    )
}
