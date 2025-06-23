package wyship.doong2.core.auth.port

import wyship.doong2.core.exception.CommonException

interface KakaoInfoPort {
    fun getKakaoInfo(query: GetKakaoInfoQuery): Result<GetKakaoInfoResult>

    data class GetKakaoInfoQuery(
        val code: String,
    )

    data class GetKakaoInfoResult(
        val email: String,
        val nickname: String,
    )

    sealed class KaKaoInfoPortException : CommonException()

    class KakaoLoginApiInternalException : KaKaoInfoPortException()

    class KakaoLoginApiClientException : KaKaoInfoPortException()

    class KakaoNameFailException : KaKaoInfoPortException()

    class KakaoEmailNotFoundException : KaKaoInfoPortException()
}
