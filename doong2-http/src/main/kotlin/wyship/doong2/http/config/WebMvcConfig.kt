package wyship.doong2.http.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import wyship.doong2.http.LANDING_CONTENT_URL
import wyship.doong2.http.LETTER_META_URL
import wyship.doong2.http.LETTER_URL
import wyship.doong2.http.LOGIN_URL

@Configuration
class WebMvcConfig(
    private val authInterceptor: AuthInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns(LOGIN_URL, LANDING_CONTENT_URL, LETTER_URL, LETTER_META_URL)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginMemberArgumentResolver())
    }
}
