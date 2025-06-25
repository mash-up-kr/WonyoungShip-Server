package wyship.doong2.http.letter.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import wyship.doong2.http.ApiResponse as CommonApiResponse

@Tag(name = "편지 쓰기 API")
annotation class LetterWriteHttpAdapterSwagger

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Operation(
    summary = "편지 작성 API",
    description = "보내는 사람, 받는 사람, 메시지, 예약 날짜, 날씨, 음악, 닉네임, 포춘쿠키 정보를 포함해 편지를 작성합니다.",
)
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "200",
            description = "성공 응답",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommonApiResponse::class),
                    examples = [
                        ExampleObject(
                            name = "성공 응답",
                            value = """
                                {
                                  "code": "0000",
                                  "message": "SUCCESS",
                                  "data": {
                                    "letterId": 123
                                  },
                                  "pageIndex": null,
                                  "pageSize": null
                                }
                            """,
                        ),
                        ExampleObject(
                            name = "요청 예시",
                            value = """
                                {
                                  "senderId": 1,
                                  "receiverId": 2,
                                  "content": "행복한 하루 보내!",
                                  "scheduleDate": "2025-07-01",
                                  "weather": "SUNNY",
                                  "musicId": 10,
                                  "senderNickname": "도운이",
                                  "fortuneCookieId": 3
                                }
                            """,
                        ),
                    ],
                ),
            ],
        ),
    ],
)
annotation class LetterWriteSwagger
