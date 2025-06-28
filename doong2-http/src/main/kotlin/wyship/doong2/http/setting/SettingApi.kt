package wyship.doong2.http.setting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.setting.CurrentSettingQueryUseCase
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.GET_SETTING_URL
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.toApiResponse

@RestController
class SettingApi(
    private val currentSettingQueryUseCase: CurrentSettingQueryUseCase,
) {
    @GetMapping(GET_SETTING_URL)
    fun getSetting(
        @LoginMember memberId: Long,
    ): ApiResponse<MemberSettingResponse> =
        currentSettingQueryUseCase
            .getCurrentSetting(memberId = memberId)
            .toApiResponse(
                onSuccess = { MemberSettingResponse(it.email, it.emailAlarm, it.tosUrl, it.privacyUrl) },
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) }, // TODO 익셉션 세분화하기.
            )

    data class MemberSettingResponse(
        val email: String,
        val emailAlarm: Boolean,
        val tosUrl: String,
        val privacyUrl: String,
    )
}
