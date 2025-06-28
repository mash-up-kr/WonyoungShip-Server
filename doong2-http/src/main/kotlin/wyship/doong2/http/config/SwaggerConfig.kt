package wyship.doong2.http.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("prod")
@OpenAPIDefinition(
    servers = [
        Server(url = "https://api.doongdoong.org", description = "API server"),
    ],
)
@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"),
                ),
            )
}

@Profile("local")
@OpenAPIDefinition(
    servers = [
        Server(url = "http://localhost:8080", description = "API server"),
    ],
)
@Configuration
class SwaggerConfigLocal {
    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"),
                ),
            )
}
