package wyship.doong2.external.kakao

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.KakaoInfoPort
import wyship.doong2.core.auth.KakaoInfoPort.GetKakaoInfoQuery
import wyship.doong2.core.auth.KakaoInfoPort.GetKakaoInfoResult

@Component
class KakaoInfoAdapter : KakaoInfoPort {
    override fun getKakaoInfo(query: GetKakaoInfoQuery): GetKakaoInfoResult {
        // TODO
        return GetKakaoInfoResult("testEmail@testEmail.com", "profileName")
    }
}
