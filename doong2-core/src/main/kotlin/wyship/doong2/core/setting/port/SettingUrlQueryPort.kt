package wyship.doong2.core.setting.port

interface SettingUrlQueryPort {
    fun getSettingUrl(): SettingUrlResult

    data class SettingUrlResult(
        val tosUrl: String,
        val privacyUrl: String,
    )
}
