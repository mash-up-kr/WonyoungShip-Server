package wyship.doong2.core.auth.domain

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MemberTokenIdGenerator {
    fun generateTokenId(): String = UUID.randomUUID().toString()
}
