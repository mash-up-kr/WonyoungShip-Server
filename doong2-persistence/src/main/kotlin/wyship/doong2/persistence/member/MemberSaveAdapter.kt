package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import wyship.doong2.core.member.MemberSavePort
import wyship.doong2.core.member.MemberSavePort.SaveMemberCommand
import wyship.doong2.core.member.MemberSavePort.SaveMemberResult

@Component
class MemberSaveAdapter(
    private val memberRepository: MemberRepository,
) : MemberSavePort {
    override fun saveMember(command: SaveMemberCommand): SaveMemberResult {
        val member =
            memberRepository.save(Member(email = command.email, name = command.name, tokenId = command.tokenId))
        return SaveMemberResult(member.id!!)
    }
}
