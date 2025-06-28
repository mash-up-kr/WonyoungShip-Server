package wyship.doong2.core.member.acl.auth

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.port.MemberExistPort
import wyship.doong2.core.member.MemberExistQueryUseCase

@Component
class MemberExistAcl(
    private val memberExistQueryUseCase: MemberExistQueryUseCase,
) : MemberExistPort {
    override fun exist(query: MemberExistPort.MemberExistQuery): MemberExistPort.MemberExistResult =
        MemberExistPort.MemberExistResult(isExist = memberExistQueryUseCase.exist(query.email))
}
