package wyship.doong2.core.auth

interface KakaoLoginUsecase {
    fun login(command: KakaoLoginCommand): Result<KakaoLoginResult>

    data class KakaoLoginCommand(
        val token: String,
    )

    data class KakaoLoginResult(
        val accessToken: String,
        val refreshToken: String,
    )
}
