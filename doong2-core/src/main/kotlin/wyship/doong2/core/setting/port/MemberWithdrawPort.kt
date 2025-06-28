package wyship.doong2.core.setting.port

interface MemberWithdrawPort {
    fun withdraw(memberId: Long): Result<Unit>
}
