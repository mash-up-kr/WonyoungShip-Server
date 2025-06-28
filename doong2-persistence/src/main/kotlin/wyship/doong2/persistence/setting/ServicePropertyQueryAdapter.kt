package wyship.doong2.persistence.setting

import org.springframework.stereotype.Component
import wyship.doong2.core.setting.port.SettingUrlQueryPort
import wyship.doong2.core.setting.port.SettingUrlQueryPort.SettingUrlResult

@Component
class ServicePropertyQueryAdapter(
    private val servicePropertyRepository: ServicePropertyRepository,
) : SettingUrlQueryPort {
    override fun getSettingUrl(): SettingUrlResult {
        val tosUrl = servicePropertyRepository.findByServiceKey("TOS_URL").serviceValue
        val privacyUrl = servicePropertyRepository.findByServiceKey("PRIVACY_POLISH_URL").serviceValue
        return SettingUrlResult(tosUrl = tosUrl, privacyUrl = privacyUrl)
    }
}
