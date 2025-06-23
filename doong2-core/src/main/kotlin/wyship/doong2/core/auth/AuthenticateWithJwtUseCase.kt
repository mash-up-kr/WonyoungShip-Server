package wyship.doong2.core.auth

import org.springframework.stereotype.Service
import wyship.doong2.core.auth.AuthenticateWithJwtUseCase.InvalidJwtExceptionException
import wyship.doong2.core.auth.AuthenticateWithJwtUseCase.NotExistMemberException
import wyship.doong2.core.auth.domain.JwtProvider
import wyship.doong2.core.auth.port.MemberTokenIdPort
import wyship.doong2.core.exception.CommonException

interface AuthenticateWithJwtUseCase {
    fun authenticateAndGetId(jwtToken: String): Result<Long>

    sealed class AuthenticateWithJwtUseCaseException : CommonException()

    class InvalidJwtExceptionException : AuthenticateWithJwtUseCaseException()

    class NotExistMemberException : AuthenticateWithJwtUseCaseException()
}

@Service
internal class AuthenticateWithJwtService(
    private val jwtProvider: JwtProvider,
    private val memberTokenIdPort: MemberTokenIdPort,
) : AuthenticateWithJwtUseCase {
    override fun authenticateAndGetId(jwtToken: String): Result<Long> {
        val tokenId =
            runCatching {
                jwtProvider.extractTokenId(jwtToken)
            }.getOrElse {
                return Result.failure(InvalidJwtExceptionException())
            }

        return memberTokenIdPort
            .getIdByTokenId(tokenId)
            .getOrElse {
                return Result.failure(NotExistMemberException())
            }.let { memberId ->
                Result.success(memberId)
            }
    }
}
