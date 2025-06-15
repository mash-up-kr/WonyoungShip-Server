package wyship.doong2.core.auth

interface AuthenticateWithKakaoUseCase {
    fun login(command: KakaoLoginCommand): Result<KakaoLoginResult>

    data class KakaoLoginCommand(
        val token: String,
    )

    data class KakaoLoginResult(
        val accessToken: String,
    )
}
