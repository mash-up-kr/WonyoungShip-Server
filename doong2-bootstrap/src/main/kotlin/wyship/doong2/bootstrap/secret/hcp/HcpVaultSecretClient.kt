package wyship.doong2.bootstrap.secret.hcp

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient

class HcpVaultSecretClient(
    private val clientId: String,
    private val clientSecret: String
) {
    private val restClient = RestClient.create()

    fun fetchSecrets(orgId: String, projectId: String, appId: String): Map<String, String> {
        val token = issueAccessToken()

        val response = restClient.get()
            .uri("$VAULT_BASE_URL/organizations/$orgId/projects/$projectId/apps/$appId$OPEN_SECRETS_PATH")
            .header(HttpHeaders.AUTHORIZATION, "$BEARER_PREFIX $token")
            .retrieve()
            .body(HcpVaultOpenResponse::class.java)
            ?: error("Vault에서 시크릿 정보를 불러오기 실패")

        return response.secrets
            .filter { it.staticVersion != null }
            .associate { it.name to it.staticVersion!!.value }
    }

    private fun issueAccessToken(): String {
        val formData = LinkedMultiValueMap<String, String>().apply {
            add(FIELD_CLIENT_ID, clientId)
            add(FIELD_CLIENT_SECRET, clientSecret)
            add(FIELD_GRANT_TYPE, GRANT_TYPE_CLIENT_CREDENTIALS)
            add(FIELD_AUDIENCE, VAULT_API_AUDIENCE)
        }

        val tokenResponse = restClient.post()
            .uri(VAULT_AUTH_URL)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(formData)
            .retrieve()
            .body(TokenResponse::class.java)
            ?: error("HCP Vault 액세스 토큰 발급 실패")

        return tokenResponse.accessToken
    }

    companion object {
        private const val VAULT_AUTH_URL = "https://auth.idp.hashicorp.com/oauth2/token"
        private const val VAULT_BASE_URL = "https://api.cloud.hashicorp.com/secrets/2023-11-28"
        private const val VAULT_API_AUDIENCE = "https://api.hashicorp.cloud"
        private const val OPEN_SECRETS_PATH = "/secrets:open"

        private const val FIELD_CLIENT_ID = "client_id"
        private const val FIELD_CLIENT_SECRET = "client_secret"
        private const val FIELD_GRANT_TYPE = "grant_type"
        private const val FIELD_AUDIENCE = "audience"
        private const val GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials"
        private const val BEARER_PREFIX = "Bearer"
    }
}

data class TokenResponse(
    @JsonProperty("access_token")
    val accessToken: String
)

data class HcpVaultOpenResponse(
    val secrets: List<OpenSecret>
)

data class OpenSecret(
    val name: String,
    @JsonProperty("static_version")
    val staticVersion: StaticVersion?
)

data class StaticVersion(
    val version: Int,
    val value: String
)
