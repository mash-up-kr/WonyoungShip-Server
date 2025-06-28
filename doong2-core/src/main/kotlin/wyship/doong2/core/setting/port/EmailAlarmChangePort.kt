package wyship.doong2.core.setting.port

interface EmailAlarmChangePort {
    fun changeEmailAlarm(
        memberId: Long,
        isOn: Boolean,
    ): Result<Unit>
}
