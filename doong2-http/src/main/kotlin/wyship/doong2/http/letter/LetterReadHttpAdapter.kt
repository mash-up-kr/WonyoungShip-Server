package wyship.doong2.http.letter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.ReadLetterUseCase
import wyship.doong2.core.letter.WriteLetterUseCase.LetterWriteFailException
import wyship.doong2.core.letter.WriteLetterUseCase.WriteLetterUseCaseException
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.HttpErrorType
import wyship.doong2.http.LETTERS_READ_URL
import wyship.doong2.http.config.LoginMember
import wyship.doong2.http.letter.doc.LetterReadHttpAdapterSwagger
import wyship.doong2.http.letter.doc.LetterReadSwagger
import wyship.doong2.http.toApiResponse
import java.time.LocalDate

@LetterReadHttpAdapterSwagger
@RestController
class LetterReadHttpAdapter(
    private val readLetterUseCase: ReadLetterUseCase,
) {

    @LetterReadSwagger
    @GetMapping(LETTERS_READ_URL)
    fun readLetters(
        @LoginMember memberId: Long,
        @RequestParam year: Int,
        @RequestParam month: Int,
    ): ApiResponse<LettersReadResponse> = readLetterUseCase
        .readByScheduleDate(ReadLetterUseCase.ReadLettersCommand(memberId, year, month))
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
            onFailure = { exception ->
                if (exception is WriteLetterUseCaseException) {
                    when (exception) {
                        is LetterWriteFailException -> ApiResponse(HttpErrorType.INVALID_WRITE_LETTER)
                    }
                } else {
                    ApiResponse(HttpErrorType.INTERNAL_ERROR)
                }
            },
        )

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
