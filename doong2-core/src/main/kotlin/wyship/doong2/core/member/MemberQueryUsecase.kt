package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByEmailException
import wyship.doong2.core.member.port.MemberQueryPort

interface MemberQueryUseCase {
    fun getByEmailOrThrow(email: String): Result<MemberResult>

    data class MemberResult(
        val id: Long,
        val email: String,
        val name: String,
        val tokenId: String,
    )

    class MemberNotFoundByEmailException : CommonException()
}

@Service
internal class MemberQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberQueryUseCase {
    override fun getByEmailOrThrow(email: String): Result<MemberQueryUseCase.MemberResult> =
        runCatching {
            val member =
                memberQueryPort.findMemberOrNull(email) ?: throw MemberNotFoundByEmailException()
            MemberQueryUseCase.MemberResult(
                member.id,
                member.email,
                member.name,
                member.tokenId,
            )
        }
}
