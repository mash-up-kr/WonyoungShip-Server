package wyship.doong2.core.member.acl.setting

import org.springframework.stereotype.Component
import wyship.doong2.core.member.MemberQueryUseCase
import wyship.doong2.core.setting.port.MemberEmailAlarmQueryPort
import wyship.doong2.core.setting.port.MemberEmailAlarmQueryPort.MemberEmailAlarmQueryResult
import wyship.doong2.core.setting.port.MemberEmailAlarmQueryPort.MemberNotFoundException

@Component
class MemberEmailAlarmQueryAcl(
    private val memberQueryUseCase: MemberQueryUseCase,
) : MemberEmailAlarmQueryPort {
    override fun getMemberEmailById(memberId: Long): Result<MemberEmailAlarmQueryResult> =
        memberQueryUseCase
            .getByIdOrThrow(id = memberId)
            .fold(
                onSuccess = { Result.success(MemberEmailAlarmQueryResult(it.email, it.emailAlarmAgreed)) },
                onFailure = { Result.failure(MemberNotFoundException()) },
            )
}
