package wyship.doong2.http.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import wyship.doong2.core.auth.AuthenticateWithJwtUseCase

@Component
class AuthInterceptor(
    private val authenticateWithJwtUseCase: AuthenticateWithJwtUseCase,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val authHeader = request.getHeader("Authorization") ?: return unauthorized(response)
        val token = authHeader.removePrefix("Bearer ").trim()

        val memberId =
            authenticateWithJwtUseCase
                .authenticateAndGetId(token)
                .getOrElse { return unauthorized(response) }

        request.setAttribute("memberId", memberId)
        return true
    }

    private fun unauthorized(response: HttpServletResponse): Boolean {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        return false
    }
}
