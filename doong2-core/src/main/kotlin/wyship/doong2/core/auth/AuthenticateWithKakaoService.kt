package wyship.doong2.core.auth

import org.springframework.stereotype.Service
import wyship.doong2.core.auth.domain.JwtProvider
import wyship.doong2.core.auth.domain.MemberTokenIdGenerator
import wyship.doong2.core.auth.port.KakaoInfoPort
import wyship.doong2.core.auth.port.KakaoInfoPort.GetKakaoInfoQuery
import wyship.doong2.core.auth.port.MemberExistPort
import wyship.doong2.core.auth.port.MemberExistPort.MemberExistQuery
import wyship.doong2.core.auth.port.MemberSignUpPort
import wyship.doong2.core.auth.port.MemberTokenIdPort

@Service
class AuthenticateWithKakaoService(
    private val memberExistPort: MemberExistPort,
    private val memberTokenIdPort: MemberTokenIdPort,
    private val memberSignUpPort: MemberSignUpPort,
    private val kakaoInfoPort: KakaoInfoPort,
    private val jwtProvider: JwtProvider,
    private val memberTokenIdGenerator: MemberTokenIdGenerator,
) : AuthenticateWithKakaoUseCase {
    override fun login(
        command: AuthenticateWithKakaoUseCase.KakaoLoginCommand,
    ): Result<AuthenticateWithKakaoUseCase.KakaoLoginResult> {
        val (email, nickname) = kakaoInfoPort.getKakaoInfo(GetKakaoInfoQuery(command.token))
        val exist = memberExistPort.exist(MemberExistQuery(email)).isExist

        if (exist.not()) {
            val tokenId = memberTokenIdGenerator.generateTokenId()
            memberSignUpPort.signUp(
                MemberSignUpPort.MemberSignUpCommand(email = email, nickname = nickname, tokenId = tokenId),
            )
        }

        val tokenId = memberTokenIdPort.getTokenId(email)
        val token = jwtProvider.createToken(tokenId)
        return Result.success(AuthenticateWithKakaoUseCase.KakaoLoginResult(token))
    }
}
