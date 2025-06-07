package wyship.doong2.core.auth

import org.springframework.stereotype.Service
import wyship.doong2.core.auth.KakaoLoginUsecase.KakaoLoginCommand
import wyship.doong2.core.auth.KakaoLoginUsecase.KakaoLoginResult

@Service
class KakaoLoginService : KakaoLoginUsecase {
    override fun login(command: KakaoLoginCommand): Result<KakaoLoginResult> {
        TODO("Not yet implemented")
    }
}
