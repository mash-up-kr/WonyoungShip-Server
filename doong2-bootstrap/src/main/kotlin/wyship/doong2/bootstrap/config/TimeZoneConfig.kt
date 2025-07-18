package wyship.doong2.bootstrap.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class TimeZoneConfig {

    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone(KST_TIMEZONE))
    }

    companion object {
        const val KST_TIMEZONE = "Asia/Seoul"
    }
}
