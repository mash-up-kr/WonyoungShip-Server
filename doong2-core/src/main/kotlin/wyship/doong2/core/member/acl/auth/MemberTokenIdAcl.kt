package wyship.doong2.core.member.acl.auth

import org.springframework.stereotype.Component
import wyship.doong2.core.auth.port.MemberTokenIdPort
import wyship.doong2.core.auth.port.MemberTokenIdPort.MemberNotFoundException
import wyship.doong2.core.auth.port.MemberTokenIdPort.MemberQueryInternalException
import wyship.doong2.core.member.MemberQueryUseCase
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByEmailException
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByTokenIdException

@Component
class MemberTokenIdAcl(
    private val memberQueryUseCase: MemberQueryUseCase,
) : MemberTokenIdPort {
    override fun getTokenId(email: String): Result<String> =
        memberQueryUseCase.getByEmailOrThrow(email).fold(
            onSuccess = { result -> Result.success(result.tokenId) },
            onFailure = { exception ->
                when (exception) {
                    is MemberNotFoundByEmailException -> Result.failure(MemberNotFoundException())
                    else -> Result.failure(MemberQueryInternalException())
                }
            },
        )

    override fun getIdByTokenId(tokenId: String): Result<Long> =
        memberQueryUseCase.getByTokenIdOrThrow(tokenId).fold(
            onSuccess = { result -> Result.success(result.id) },
            onFailure = { exception ->
                when (exception) {
                    is MemberNotFoundByTokenIdException -> Result.failure(MemberNotFoundException())
                    else -> Result.failure(MemberQueryInternalException())
                }
            },
        )
}
