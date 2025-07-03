package wyship.doong2.http.setting

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.setting.CurrentSettingQueryUseCase
import wyship.doong2.core.setting.EmailAlarmChangeUseCase
import wyship.doong2.core.setting.WithdrawUseCase
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.EMAIL_ALARM_SETTING_URL
import wyship.doong2.http.GET_SETTING_URL
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.WITHDRAW_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.toApiResponse

@Tag(name = "설정 API")
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class SettingApi(
    private val currentSettingQueryUseCase: CurrentSettingQueryUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val emailAlarmChangeUseCase: EmailAlarmChangeUseCase,
) {
    @Operation(
        summary = "설정 조회",
        description = "설정 상태를 조회합니다",
    )
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

    @Operation(
        summary = "계정 탈퇴",
        description = "현재 계정 탈퇴합니다",
    )
    @PostMapping(WITHDRAW_URL)
    fun withdraw(
        @LoginMember memberId: Long,
    ): ApiResponse<Unit> =
        withdrawUseCase
            .withdraw(memberId)
            .toApiResponse(
                onSuccess = { },
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) }, // TODO 익셉션 세분화하기.
            )

    @Operation(
        summary = "이메일 설정",
        description = "이메일 관련 설정을 합니다",
    )
    @PostMapping(EMAIL_ALARM_SETTING_URL)
    fun changeEmailAlarm(
        @LoginMember memberId: Long,
        @RequestBody request: ChangeEmailSettingRequest,
    ): ApiResponse<Unit> =
        emailAlarmChangeUseCase
            .changeEmailAlarm(memberId, request.isOn)
            .toApiResponse(
                onSuccess = { },
                onFailure = { ApiResponse(HttpErrorType.INTERNAL_ERROR) }, // TODO 익셉션 세분화하기.
            )

    data class ChangeEmailSettingRequest(
        val isOn: Boolean,
    )

    data class MemberSettingResponse(
        val email: String,
        val emailAlarm: Boolean,
        val tosUrl: String,
        val privacyUrl: String,
    )
}
