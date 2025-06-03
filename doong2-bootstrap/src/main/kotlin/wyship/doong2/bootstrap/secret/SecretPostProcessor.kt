package wyship.doong2.bootstrap.secret

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource

class SecretPostProcessor : EnvironmentPostProcessor {

    override fun postProcessEnvironment(env: ConfigurableEnvironment, application: SpringApplication?) {
        val loader = SecretLoaderFactory.create(env)
        val secrets = loader.load()

        val propertySource = MapPropertySource(PROPERTY_SOURCE_NAME, secrets)
        env.propertySources.addFirst(propertySource)
    }

    companion object {
        private const val PROPERTY_SOURCE_NAME = "envSecret"
    }
}
