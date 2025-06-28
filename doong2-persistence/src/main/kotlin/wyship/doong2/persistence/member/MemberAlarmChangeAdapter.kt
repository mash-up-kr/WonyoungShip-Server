package wyship.doong2.persistence.member

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.member.port.MemberEmailAlarmChangePort

@Component
class MemberAlarmChangeAdapter(
    private val memberRepository: MemberRepository,
) : MemberEmailAlarmChangePort {
    @Transactional
    override fun offEmailAlarm(memberId: Long): Result<Unit> =
        runCatching {
            val member = memberRepository.findMemberById(memberId) ?: throw IllegalStateException()
            member.emailAlarmAgreed = false
        }

    @Transactional
    override fun onEmailAlarm(memberId: Long): Result<Unit> =
        runCatching {
            val member = memberRepository.findMemberById(memberId) ?: throw IllegalStateException()
            member.emailAlarmAgreed = true
        }
}
