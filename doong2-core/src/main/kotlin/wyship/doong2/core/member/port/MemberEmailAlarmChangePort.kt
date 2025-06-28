package wyship.doong2.core.member.port

interface MemberEmailAlarmChangePort {
    fun offEmailAlarm(memberId: Long): Result<Unit>

    fun onEmailAlarm(memberId: Long): Result<Unit>
}
