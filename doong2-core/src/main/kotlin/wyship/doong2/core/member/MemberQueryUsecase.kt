package wyship.doong2.core.member

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByEmailException
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByIdException
import wyship.doong2.core.member.MemberQueryUseCase.MemberNotFoundByTokenIdException
import wyship.doong2.core.member.MemberQueryUseCase.MemberResult
import wyship.doong2.core.member.port.MemberQueryPort

interface MemberQueryUseCase {
    fun getByEmailOrThrow(email: String): Result<MemberResult>

    fun getByTokenIdOrThrow(tokenId: String): Result<MemberResult>

    fun getByIdOrThrow(id: Long): Result<MemberResult>

    data class MemberResult(
        val id: Long,
        val email: String,
        val name: String,
        val tokenId: String,
    )

    class MemberNotFoundByEmailException : CommonException()

    class MemberNotFoundByTokenIdException : CommonException()

    class MemberNotFoundByIdException : CommonException()
}

@Service
internal class MemberQueryService(
    private val memberQueryPort: MemberQueryPort,
) : MemberQueryUseCase {
    override fun getByEmailOrThrow(email: String): Result<MemberResult> =
        runCatching {
            val member =
                memberQueryPort.findMemberByEmailOrNull(email) ?: throw MemberNotFoundByEmailException()
            MemberResult(
                member.id,
                member.email,
                member.name,
                member.tokenId,
            )
        }

    override fun getByTokenIdOrThrow(tokenId: String): Result<MemberResult> =
        runCatching {
            val member =
                memberQueryPort.findMemberByTokenIdOrNull(tokenId) ?: throw MemberNotFoundByTokenIdException()
            MemberResult(
                member.id,
                member.email,
                member.name,
                member.tokenId,
            )
        }

    override fun getByIdOrThrow(id: Long): Result<MemberResult> =
        runCatching {
            val member =
                memberQueryPort.findMemberByIdOrNull(id) ?: throw MemberNotFoundByIdException()
            MemberResult(
                member.id,
                member.email,
                member.name,
                member.tokenId,
            )
        }
}
