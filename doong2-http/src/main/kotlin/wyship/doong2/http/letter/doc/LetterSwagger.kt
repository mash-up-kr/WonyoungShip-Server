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
    description = "보내는 사람, 받는 사람, 메시지, 예약 날짜, 꾸밈 요소를 포함해 편지를 작성합니다.",
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
                            name = "편지 작성 성공",
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
                    ],
                ),
            ],
        ),
    ],
)
annotation class LetterWriteSwagger
