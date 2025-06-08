package wyship.doong2.core.member

import org.springframework.stereotype.Service

@Service
class MemberQueryService : MemberQueryUsecase {
    override fun getByEmail(email: String): MemberQueryUsecase.MemberResult {
        // TODO
        return MemberQueryUsecase.MemberResult(1L, "testEmail@testEmail.com", "profileName", "testTokenId")
    }
}
