package wyship.doong2.http.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.AuthenticateWithKakaoUseCaseException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginCommand
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginEmailFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginTokenFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoUseCaseLoginNameFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.MemberSignUpFailException
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LOGIN_URL
import wyship.doong2.http.toApiResponse

@Tag(name = "카카오 로그인 API")
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class KakaoLoginApi(
    private val authenticateWithKakaoUseCaseService: AuthenticateWithKakaoUseCase,
) {
    @Operation(
        summary = "카카오 로그인",
        description = "카카오 토큰으로 로그인합니다",
    )
    @PostMapping(LOGIN_URL)
    fun kakaoLogin(
        @RequestBody request: KakaoLoginRequest,
    ): ApiResponse<KakaoLoginResponse> =
        authenticateWithKakaoUseCaseService
            .login(request.toCommand())
            .toApiResponse(
                onSuccess = {
                    KakaoLoginResponse(it.accessToken)
                },
                onFailure = { exception ->
                    if (exception is AuthenticateWithKakaoUseCaseException) {
                        when (exception) {
                            is KakaoUseCaseLoginNameFailException ->
                                ApiResponse(
                                    HttpErrorType.INVALID_KAKAO_NAME_PERMISSION,
                                )
                            is KakaoLoginEmailFailException -> ApiResponse(HttpErrorType.INVALID_KAKAO_EMAIL_PERMISSION)
                            is KakaoLoginTokenFailException -> ApiResponse(HttpErrorType.INVALID_KAKAO_LOGIN_TOKEN)
                            is MemberSignUpFailException -> ApiResponse(HttpErrorType.INVALID_SIGN_UP_STATUS)
                            is KakaoLoginFailException -> ApiResponse(HttpErrorType.INTERNAL_ERROR)
                        }
                    } else {
                        ApiResponse(HttpErrorType.INTERNAL_ERROR)
                    }
                },
            )

    data class KakaoLoginRequest(
        val token: String,
        val url: String,
    ) {
        fun toCommand(): KakaoLoginCommand = KakaoLoginCommand(token = token, redirectUrl = url)
    }

    data class KakaoLoginResponse(
        val accessToken: String,
    )
}
