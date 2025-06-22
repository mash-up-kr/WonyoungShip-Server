package wyship.doong2.core.auth

import org.springframework.stereotype.Service
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginCommand
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginEmailFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginResult
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoLoginTokenFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.KakaoUseCaseLoginNameFailException
import wyship.doong2.core.auth.AuthenticateWithKakaoUseCase.MemberSignUpFailException
import wyship.doong2.core.auth.domain.JwtProvider
import wyship.doong2.core.auth.domain.MemberTokenIdGenerator
import wyship.doong2.core.auth.port.KakaoInfoPort
import wyship.doong2.core.auth.port.KakaoInfoPort.GetKakaoInfoQuery
import wyship.doong2.core.auth.port.KakaoInfoPort.KaKaoInfoPortException
import wyship.doong2.core.auth.port.MemberSignUpPort
import wyship.doong2.core.auth.port.MemberTokenIdPort
import wyship.doong2.core.exception.CommonException

interface AuthenticateWithKakaoUseCase {
    fun login(command: KakaoLoginCommand): Result<KakaoLoginResult>

    data class KakaoLoginCommand(
        val token: String,
    )

    data class KakaoLoginResult(
        val accessToken: String,
    )

    sealed class AuthenticateWithKakaoUseCaseException : CommonException()

    class KakaoLoginFailException : AuthenticateWithKakaoUseCaseException()

    class KakaoLoginTokenFailException : AuthenticateWithKakaoUseCaseException()

    class KakaoUseCaseLoginNameFailException : AuthenticateWithKakaoUseCaseException()

    class KakaoLoginEmailFailException : AuthenticateWithKakaoUseCaseException()

    class MemberSignUpFailException : AuthenticateWithKakaoUseCaseException()
}

@Service
internal class AuthenticateWithKakaoService(
    private val memberTokenIdPort: MemberTokenIdPort,
    private val memberSignUpPort: MemberSignUpPort,
    private val kakaoInfoPort: KakaoInfoPort,
    private val jwtProvider: JwtProvider,
    private val memberTokenIdGenerator: MemberTokenIdGenerator,
) : AuthenticateWithKakaoUseCase {
    override fun login(command: KakaoLoginCommand): Result<KakaoLoginResult> =
        runCatching {
            val (email, nickname) =
                kakaoInfoPort
                    .getKakaoInfo(GetKakaoInfoQuery(command.token))
                    .getOrElse { exception ->
                        if (exception is KaKaoInfoPortException) {
                            when (exception) {
                                is KakaoInfoPort.KakaoLoginApiInternalException -> throw KakaoLoginFailException()
                                is KakaoInfoPort.KakaoNameFailException -> throw KakaoUseCaseLoginNameFailException()
                                is KakaoInfoPort.KakaoEmailNotFoundException -> throw KakaoLoginEmailFailException()
                                is KakaoInfoPort.KakaoLoginApiClientException -> throw KakaoLoginTokenFailException()
                            }
                        } else {
                            throw KakaoLoginFailException()
                        }
                    }

            if (memberSignUpPort.isAlreadySignUp(email)) {
                memberSignUpPort
                    .signUp(
                        MemberSignUpPort.MemberSignUpCommand(
                            email = email,
                            nickname = nickname,
                            tokenId = memberTokenIdGenerator.generateTokenId(),
                        ),
                    ).getOrElse { throw MemberSignUpFailException() }
            }

            val tokenId = memberTokenIdPort.getTokenId(email).getOrElse { throw MemberSignUpFailException() }
            val token = jwtProvider.createToken(tokenId)

            return@runCatching KakaoLoginResult(token)
        }
}
