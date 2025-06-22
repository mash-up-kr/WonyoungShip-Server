package wyship.doong2.http

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import wyship.doong2.core.exception.CommonException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMediaTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMediaTypeException(e: HttpMediaTypeException): ApiResponse<Any> {
        log.info("handleHttpMediaTypeException: {}", e.message, e)
        return ApiResponse(HttpErrorType.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: MethodArgumentTypeMismatchException): ApiResponse<Any> {
        log.info("handleMethodArgumentTypeMismatchException: {}", e.message, e)
        return ApiResponse(HttpErrorType.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: Exception): ApiResponse<Any> {
        log.error("handleIllegalArgumentException", e)
        return ApiResponse(HttpErrorType.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalStateException(e: Exception): ApiResponse<Any> {
        log.error("handleIllegalStateException", e)
        return ApiResponse(HttpErrorType.BAD_REQUEST)
    }

    @ExceptionHandler(CommonException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleCommonException(e: Exception): ApiResponse<Any> {
        log.error("handleCommonException", e)
        return ApiResponse(HttpErrorType.INTERNAL_ERROR)
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(e: Exception): ApiResponse<Any> {
        log.error("handleRuntimeException", e)
        return ApiResponse(HttpErrorType.INTERNAL_ERROR)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}
