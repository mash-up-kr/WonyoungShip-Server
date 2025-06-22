package wyship.doong2.core.member.acl

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.port.MemberTokenIdPort
import wyship.doong2.core.auth.port.MemberTokenIdPort.MemberNotFoundException
import wyship.doong2.core.auth.port.MemberTokenIdPort.MemberQueryInternalException
import wyship.doong2.core.member.MemberQueryUseCase
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByEmailException

@Component
class MemberTokenIdAcl(
    private val memberQueryUseCase: MemberQueryUseCase,
) : MemberTokenIdPort {
    override fun getTokenId(email: String): Result<String> =
        memberQueryUseCase.getByEmailOrThrow(email).fold(
            onSuccess = { result -> Result.success(result.email) },
            onFailure = { exception ->
                when (exception) {
                    is MemberNotFoundByEmailException -> Result.failure(MemberNotFoundException())
                    else -> Result.failure(MemberQueryInternalException())
                }
            },
        )
}
