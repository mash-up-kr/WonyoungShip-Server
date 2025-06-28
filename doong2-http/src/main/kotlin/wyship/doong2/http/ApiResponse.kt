package wyship.doong2.http

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null,
    val pageIndex: Int? = null,
    val pageSize: Int? = null,
) {
    constructor(errorType: HttpErrorType) : this(errorType.code, errorType.message, null, null, null)

    companion object {
        @JvmStatic
        fun success(): ApiResponse<Unit> = ApiResponse("0000", "SUCCESS")

        @JvmStatic
        fun <T> success(result: T): ApiResponse<T> {
            val data = if (result == Unit) null else result
            return ApiResponse("0000", "SUCCESS", data, null, null)
        }
    }
}

inline fun <T, R> Result<T>.toApiResponse(
    onSuccess: (T) -> R,
    onFailure: (Throwable) -> ApiResponse<R>,
): ApiResponse<R> =
    fold(
        onSuccess = {
            val result = onSuccess(it)
            ApiResponse.success(result)
        },
        onFailure = { onFailure(it) },
    )
