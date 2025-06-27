package wyship.doong2.http.letter.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import wyship.doong2.http.ApiResponse as CommonApiResponse

@Tag(name = "편지 조회 API")
annotation class LetterReadHttpAdapterSwagger

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Operation(
    summary = "편지 목록 조회 API",
    description = "특정 년도, 월에 해당하는 편지 목록을 조회합니다.",
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
                    ],
                ),
            ],
        ),
    ],
)
annotation class LetterReadSwagger
