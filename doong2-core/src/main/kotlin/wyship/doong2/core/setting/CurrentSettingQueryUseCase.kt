package wyship.doong2.core.setting

import org.springframework.stereotype.Service
import wyship.doong2.core.exception.CommonException
import wyship.doong2.core.setting.CurrentSettingQueryUseCase.CurrentSettingQueryResult
import wyship.doong2.core.setting.CurrentSettingQueryUseCase.MemberNotFoundException
import wyship.doong2.core.setting.port.MemberEmailAlarmQueryPort
import wyship.doong2.core.setting.port.SettingUrlQueryPort

interface CurrentSettingQueryUseCase {
    fun getCurrentSetting(memberId: Long): Result<CurrentSettingQueryResult>

    class MemberNotFoundException : CommonException()

    data class CurrentSettingQueryResult(
        val email: String,
        val emailAlarm: Boolean,
        val tosUrl: String,
        val privacyUrl: String,
    )
}

@Service
internal class CurrentSettingQueryService(
    private val memberEmailAlarmQueryPort: MemberEmailAlarmQueryPort,
    private val settingUrlQueryPort: SettingUrlQueryPort,
) : CurrentSettingQueryUseCase {
    override fun getCurrentSetting(memberId: Long): Result<CurrentSettingQueryResult> =
        runCatching {
            val (email, agreed) =
                memberEmailAlarmQueryPort
                    .getMemberEmailById(memberId = memberId)
                    .getOrElse {
                        throw MemberNotFoundException()
                    }
            val (tosUrl, privacyUrl) = settingUrlQueryPort.getSettingUrl()

            return@runCatching CurrentSettingQueryResult(
                email = email,
                emailAlarm = agreed,
                tosUrl = tosUrl,
                privacyUrl = privacyUrl,
            )
        }
}
