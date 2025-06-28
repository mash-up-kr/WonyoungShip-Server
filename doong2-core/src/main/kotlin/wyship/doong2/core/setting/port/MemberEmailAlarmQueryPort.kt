package wyship.doong2.core.setting.port

import wyship.doong2.core.exception.CommonException

interface MemberEmailAlarmQueryPort {
    fun getMemberEmailById(memberId: Long): Result<MemberEmailAlarmQueryResult>

    class MemberNotFoundException : CommonException()

    data class MemberEmailAlarmQueryResult(
        val email: String,
        val agreed: Boolean,
    )
}
