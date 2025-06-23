package wyship.doong2.http

enum class HttpErrorType(
    val code: String,
    val message: String,
) {
    BAD_REQUEST("4000", "bad request"),

    // auth fail
    INVALID_KAKAO_LOGIN_TOKEN("4100", "kakao login token is invalid"),
    INVALID_KAKAO_NAME_PERMISSION("4101", "can not get kakao login name"),
    INVALID_KAKAO_EMAIL_PERMISSION("4102", "can not get kakao login email"),

    // sign up fail
    INVALID_SIGN_UP_STATUS("4201", "invalid sign up status"),

    INTERNAL_ERROR("5000", "internal error"),
}
