package wyship.doong2.core.auth.domain

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.validityMs}") private val validityMs: Long,
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    private val parser =
        Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()

    fun createToken(tokenId: String): String {
        val now = Date()
        val expiry = Date(now.time + validityMs)

        return Jwts
            .builder()
            .setSubject(tokenId)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractTokenId(token: String): String = parser.parseClaimsJws(token).body.subject
}
