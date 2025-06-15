package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.MemberSavePort
import wyship.doong2.core.member.MemberSavePort.SaveMemberCommand
import wyship.doong2.core.member.MemberSavePort.SaveMemberResult

@Component
class MemberSaveAdapter(
    private val memberRepository: MemberRepository,
) : MemberSavePort {
    @Transactional
    override fun saveMember(command: SaveMemberCommand): SaveMemberResult {
        val member =
            memberRepository.save(
                Members(email = command.email, nickname = command.nickname, tokenId = command.tokenId),
            )
        return SaveMemberResult(member.id!!)
    }
}
