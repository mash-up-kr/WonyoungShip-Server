package wyship.doong2.core.auth

import org.springframework.stereotype.Service

interface LogoutUseCase {
    fun logout(id: Long)
}

@Service
internal class LogoutService : LogoutUseCase {
    override fun logout(id: Long) {
        TODO("Not yet implemented")
    }
}
