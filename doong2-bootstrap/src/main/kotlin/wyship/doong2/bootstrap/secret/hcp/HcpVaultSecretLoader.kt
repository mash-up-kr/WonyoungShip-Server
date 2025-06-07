package wyship.doong2.bootstrap.secret.hcp

import org.springframework.boot.context.properties.bind.Bindable
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.core.env.ConfigurableEnvironment
import wyship.doong2.bootstrap.secret.SecretLoader

class HcpVaultSecretLoader(
    private val env: ConfigurableEnvironment,
) : SecretLoader {
    private val profile: String = env.activeProfiles.firstOrNull() ?: DEFAULT_PROFILE

    override fun load(): Map<String, String> {
        val clientId = getOrThrow(PROP_CLIENT_ID)
        val clientSecret = getOrThrow(PROP_CLIENT_SECRET)
        val orgId = getOrThrow(PROP_ORG_ID)
        val projectId = getOrThrow(PROP_PROJECT_ID)
        val appId = getAppIdForProfile(profile)

        val client = HcpVaultSecretClient(clientId, clientSecret)
        return client.fetchSecrets(orgId, projectId, appId)
    }

    private fun getAppIdForProfile(profile: String): String {
        val map: Map<String, String> =
            Binder
                .get(env)
                .bind(PROP_APP_ID, Bindable.mapOf(String::class.java, String::class.java))
                .orElseThrow { error("property '$PROP_APP_ID'이(가) 없음") }

        return map[profile]
            ?: error("프로필 '$profile'에 해당하는 App ID가 '$PROP_APP_ID'에 설정되어 있지 않음")
    }

    private fun getOrThrow(key: String): String = env.getProperty(key) ?: error("할당 실패한 property: $key")

    companion object {
        private const val DEFAULT_PROFILE = "default"

        private const val PROP_CLIENT_ID = "vault.hcp.client-id"
        private const val PROP_CLIENT_SECRET = "vault.hcp.client-secret"
        private const val PROP_ORG_ID = "vault.hcp.org-id"
        private const val PROP_PROJECT_ID = "vault.hcp.project-id"
        private const val PROP_APP_ID = "vault.hcp.app-id"
    }
}
