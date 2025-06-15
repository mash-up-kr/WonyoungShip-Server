package wyship.doong2.http.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.auth.AuthenticateWithKakaoService
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LOGIN_URL
import wyship.doong2.http.toApiResponse

@RestController
class KakaoLoginHttpAdapter(
    private val authenticateWithKakaoUseCaseService: AuthenticateWithKakaoService,
) {
    @PostMapping(LOGIN_URL)
    fun kakaoLogin(
        @RequestBody request: KakaoLoginRequest,
    ): ApiResponse<KakaoLoginResponse> =
        authenticateWithKakaoUseCaseService
            .login(request.toCommand())
            .toApiResponse(
                {
                    KakaoLoginResponse(it.accessToken)
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
        fun toCommand(): AuthenticateWithKakaoUseCase.KakaoLoginCommand =
            AuthenticateWithKakaoUseCase.KakaoLoginCommand(token)
    }

    data class KakaoLoginResponse(
        val accessToken: String,
    )
}
