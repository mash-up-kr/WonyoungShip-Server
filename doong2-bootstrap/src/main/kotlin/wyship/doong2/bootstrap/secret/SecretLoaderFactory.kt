package wyship.doong2.bootstrap.secret

import org.springframework.core.env.ConfigurableEnvironment
import wyship.doong2.bootstrap.secret.hcp.HcpVaultSecretLoader

object SecretLoaderFactory {
    fun create(env: ConfigurableEnvironment): SecretLoader =
        when (env.getProperty("vault.provider") ?: "hcp") {
            "hcp" -> HcpVaultSecretLoader(env)
            else -> error("잘못된 Secret Manager : ${env.getProperty("vault.provider")}")
        }
}
