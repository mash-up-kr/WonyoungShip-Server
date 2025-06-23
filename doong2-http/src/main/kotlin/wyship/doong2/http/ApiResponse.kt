package wyship.doong2.http

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null,
    val pageIndex: Int? = null,
    val pageSize: Int? = null,
) {
    constructor(errorType: HttpErrorType) : this(errorType.code, errorType.message, null, null, null)
}

inline fun <T, R> Result<T>.toApiResponse(
    onSuccess: (T) -> R,
    onFailure: (Throwable) -> ApiResponse<R>,
): ApiResponse<R> =
    fold(
        onSuccess = {
            val result = onSuccess(it)
            ApiResponse(
                code = "0000",
                message = "SUCCESS",
                data = if (result == Unit) null else result,
                pageIndex = null,
                pageSize = null,
            )
        },
        onFailure = { onFailure(it) },
    )
