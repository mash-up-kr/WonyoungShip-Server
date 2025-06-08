package wyship.doong2.core.auth.domain

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
) {
    private val validityMs = 3600000L // 1 hour
    private val parser: JwtParser = Jwts.parser().setSigningKey(secretKey.toByteArray())

    fun createToken(tokenId: String): String {
        val claims = Jwts.claims().setSubject(tokenId)
        val now = Date()
        val expiry = Date(now.time + validityMs)

        return Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
            .compact()
    }

    fun extractTokenId(token: String): String =
        parser
            .parseClaimsJws(token)
            .body
            .subject
}
