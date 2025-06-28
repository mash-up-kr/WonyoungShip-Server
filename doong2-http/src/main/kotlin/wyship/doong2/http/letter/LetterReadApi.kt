package wyship.doong2.http.letter

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.LetterReadUseCase
import wyship.doong2.core.letter.LetterReadUseCase.LetterFailExceptionRead
import wyship.doong2.core.letter.LetterReadUseCase.LetterReadUseCaseException
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.model.command.LettersReadCommand
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LETTER_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.letter.doc.LetterReadApiSwagger
import wyship.doong2.http.letter.doc.LetterReadSwagger
import wyship.doong2.http.letter.model.LetterDetailResponse
import wyship.doong2.http.letter.model.LettersDailyResponse
import wyship.doong2.http.letter.model.LettersWeeklyCountResponse
import wyship.doong2.http.toApiResponse
import java.time.LocalDate

@LetterReadApiSwagger
@RestController
@RequestMapping(LETTER_URL)
class LetterReadApi(
    private val letterReadUseCase: LetterReadUseCase,
) {

    @Operation(
        summary = "편지 상세 조회",
        description = "읽을 수 있는 편지의 상세 정보를 조회합니다.",
    )
    @GetMapping("/detail/{letterId}")
    fun readDetailLetter(
        @LoginMember memberId: Long,
        @PathVariable letterId: Long,
    ): ApiResponse<LetterDetailResponse> = letterReadUseCase
        .readDetailLetter(memberId, letterId)
        .toApiResponse(
            onSuccess = { LetterDetailResponse.from(it) },
            onFailure = { onFailure(it) },
        )

    @Operation(
        summary = "일간 편지 목록 정보 조회",
        description = "특정한 날에 받은 편지 목록을 조회합니다.",
    )
    @GetMapping("/daily")
    fun readDailyLetters(
        @LoginMember memberId: Long,
        @RequestParam date: LocalDate,
    ): ApiResponse<LettersDailyResponse> = letterReadUseCase
        .readDailyReceivedLetters(memberId, date)
        .toApiResponse(
            onSuccess = { LettersDailyResponse.from(it) },
            onFailure = { onFailure(it) },
        )

    @Operation(
        summary = "주간 편지 개수 정보 조회",
        description = "이번 주에 받은 편지 개수를 조회합니다.",
    )
    @GetMapping("/count/weekly")
    fun readWeeklyCount(
        @LoginMember memberId: Long,
    ): ApiResponse<LettersWeeklyCountResponse> = letterReadUseCase
        .countWeeklyReceivedLetters(memberId)
        .toApiResponse(
            onSuccess = { LettersWeeklyCountResponse.from(it) },
            onFailure = { onFailure(it) },
        )

    @LetterReadSwagger
    @GetMapping
    fun readLetters(
        @LoginMember memberId: Long,
        @RequestParam year: Int,
        @RequestParam month: Int,
    ): ApiResponse<LettersReadResponse> = letterReadUseCase
        .readByScheduleDate(LettersReadCommand(memberId, year, month))
        .toApiResponse(
            onSuccess = { result ->
                LettersReadResponse(
                    year = year,
                    month = month,
                    letters = result.letters.map { letter ->
                        LetterReadResponse(
                            senderNickName = letter.senderNickName,
                            createdDate = letter.createdDate,
                            scheduleDate = letter.scheduleDate,
                            weatherType = letter.weatherType,
                            content = letter.content,
                            music = letter.music?.let {
                                LetterMusicReadResponse(
                                    title = it.title,
                                    artist = it.artist,
                                    url = it.url,
                                )
                            },
                            fortuneCookieId = letter.fortuneCookieId,
                        )
                    },
                )
            },
            onFailure = { onFailure(it) },
        )

    fun <T> onFailure(exception: Throwable): ApiResponse<T> {
        val letterException = exception as? LetterReadUseCaseException
        return when (letterException) {
            is LetterFailExceptionRead -> ApiResponse(HttpErrorType.INVALID_READ_LETTER)
            null -> ApiResponse(HttpErrorType.INTERNAL_ERROR)
            else -> ApiResponse(HttpErrorType.INTERNAL_ERROR)
        }
    }

    data class LettersReadResponse(
        val year: Int,
        val month: Int,
        val letters: List<LetterReadResponse>,
    )

    data class LetterReadResponse(
        val senderNickName: String,
        val createdDate: LocalDate,
        val scheduleDate: LocalDate,
        val weatherType: WeatherType,
        val content: String,
        val music: LetterMusicReadResponse?,
        val fortuneCookieId: Long?,
    )

    data class LetterMusicReadResponse(
        val title: String,
        val artist: String,
        val url: String,
    )
}
