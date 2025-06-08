package wyship.doong2.core.auth

interface KakaoLoginUsecase {
    /**
     * kakao 로 로그인한다.
     * token으로 받아온 이메일로 가입된 멤버가 있을 경우, 인증 토큰을 응답한다.
     * token으로 받아온 이메일로 가입된 멤버가 없을 경우, 회원 가입 후 인증 토큰을 응답한다.
     */
    fun login(command: KakaoLoginCommand): Result<KakaoLoginResult>

    data class KakaoLoginCommand(
        val token: String,
    )

    data class KakaoLoginResult(
        val accessToken: String,
    )
}
