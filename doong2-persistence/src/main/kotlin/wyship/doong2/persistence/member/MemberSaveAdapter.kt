package wyship.doong2.persistence.member

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
    override fun saveMember(command: SaveMemberCommand): SaveMemberResult {
        val member =
            memberRepository.save(
                Member(email = command.email, nickname = command.nickname, tokenId = command.tokenId),
            )
        return SaveMemberResult(member.id!!)
    }
}
