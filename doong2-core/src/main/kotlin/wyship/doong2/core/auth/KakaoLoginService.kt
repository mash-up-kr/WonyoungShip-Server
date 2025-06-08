package wyship.doong2.core.auth

import org.springframework.stereotype.Service
import wyship.doong2.core.auth.KakaoInfoPort.GetKakaoInfoQuery
import wyship.doong2.core.auth.KakaoLoginUsecase.KakaoLoginCommand
import wyship.doong2.core.auth.KakaoLoginUsecase.KakaoLoginResult
import wyship.doong2.core.auth.MemberExistPort.MemberExistQuery
import wyship.doong2.core.auth.domain.JwtProvider

@Service
class KakaoLoginService(
    private val memberExistPort: MemberExistPort,
    private val memberTokenIdPort: MemberTokenIdPort,
    private val memberSignUpPort: MemberSignUpPort,
    private val kakaoInfoPort: KakaoInfoPort,
    private val jwtProvider: JwtProvider,
) : KakaoLoginUsecase {
    override fun login(command: KakaoLoginCommand): Result<KakaoLoginResult> {
        val (email, name) = kakaoInfoPort.getKakaoInfo(GetKakaoInfoQuery(command.token))
        val exist = memberExistPort.exist(MemberExistQuery(email)).isExist

        if (exist.not()) {
            memberSignUpPort.signUp(email, name)
        }

        val tokenId = memberTokenIdPort.getTokenId(email)
        val token = jwtProvider.createToken(tokenId)
        return Result.success(KakaoLoginResult(token))
    }
}
