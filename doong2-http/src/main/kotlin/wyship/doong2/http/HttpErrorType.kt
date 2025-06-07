package wyship.doong2.http

enum class HttpErrorType(
    val code: String,
    val message: String,
) {
    INVALID_KAKAO_LOGIN_TOKEN("4100", "kakao login token is invalid"),
}
