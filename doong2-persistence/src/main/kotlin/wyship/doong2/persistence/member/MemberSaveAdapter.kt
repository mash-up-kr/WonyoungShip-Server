package wyship.doong2.persistence.member

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.MemberSavePort
import wyship.doong2.core.member.port.MemberSavePort.SaveMemberCommand
import wyship.doong2.core.member.port.MemberSavePort.SaveMemberResult

@Component
class MemberSaveAdapter(
    private val memberRepository: MemberRepository,
) : MemberSavePort {
    @Transactional
    override fun saveMember(command: SaveMemberCommand): Result<SaveMemberResult> =
        runCatching {
            val memberEntity =
                memberRepository.save(
                    MemberEntity(
                        email = command.email,
                        nickname = command.nickname,
                        tokenId = command.tokenId,
                        emailAlarmAgreed = true,
                    ),
                )
            SaveMemberResult(
                memberEntity.id
                    ?: throw IllegalStateException(
                        "[MemberSaveAdapter][saveMember] member id is null. check orm or transaction",
                    ),
            )
        }.onFailure { exception ->
            log.warn("[MemberSaveAdapter][saveMember] save ${command.email} member fail exception: $exception")
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(MemberSavePort.MemberSaveFailException()) },
        )

    companion object {
        private val log = LoggerFactory.getLogger(MemberSaveAdapter::class.java)
    }
}
