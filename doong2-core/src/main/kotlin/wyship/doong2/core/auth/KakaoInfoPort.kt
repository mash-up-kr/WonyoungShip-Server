package wyship.doong2.core.auth

interface KakaoInfoPort {
    fun getKakaoInfo(query: GetKakaoInfoQuery): GetKakaoInfoResult

    data class GetKakaoInfoQuery(
        val code: String,
    )

    data class GetKakaoInfoResult(
        val email: String,
        val name: String,
    )
}
