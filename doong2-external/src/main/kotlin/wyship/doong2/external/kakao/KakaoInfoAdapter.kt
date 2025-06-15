package wyship.doong2.external.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import wyship.doong2.core.auth.port.KakaoInfoPort
import wyship.doong2.core.auth.port.KakaoInfoPort.GetKakaoInfoQuery
import wyship.doong2.core.auth.port.KakaoInfoPort.GetKakaoInfoResult

@Component
class KakaoInfoAdapter(
    @Value("\${kakao.auth.clientId}") private val clientId: String,
    @Value("\${kakao.auth.redirectUri}") private val redirectUri: String,
    private val restClient: RestClient,
) : KakaoInfoPort {
    override fun getKakaoInfo(query: GetKakaoInfoQuery): GetKakaoInfoResult {
        val formData: MultiValueMap<String, String> =
            LinkedMultiValueMap<String, String>().apply {
                add("grant_type", "authorization_code")
                add("client_id", clientId)
                add("redirect_uri", redirectUri)
                add("code", query.code)
            }

        val tokenResponse =
            restClient
                .post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(formData)
                .retrieve()
                .body(KakaoTokenResponse::class.java) ?: throw throw IllegalStateException()

        val userResponse =
            restClient
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer ${tokenResponse.accessToken}")
                .retrieve()
                .body(KakaoUserInfoResponse::class.java) ?: throw throw IllegalStateException()

        val email = userResponse.kakaoAccount.email ?: throw IllegalStateException("이메일 정보가 없습니다. 동의 항목을 확인하세요.")
        val nickname = userResponse.kakaoAccount.name ?: throw IllegalStateException("이름 정보가 없습니다. 동의 항목을 확인하세요.")

        return GetKakaoInfoResult(email = email, nickname = nickname)
    }

    data class KakaoTokenResponse(
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("expires_in") val expiresIn: Int,
        @JsonProperty("refresh_token") val refreshToken: String?,
        @JsonProperty("scope") val scope: String,
    )

    data class KakaoUserInfoResponse(
        @JsonProperty("id") val id: Long,
        @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount,
    )

    data class KakaoAccount(
        @JsonProperty("email") val email: String?,
        @JsonProperty("name") val name: String?,
    )
}
