package wyship.doong2.http.auth.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "카카오 로그인 API")
annotation class KakaoLoginApiSwagger

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Operation(
    summary = "카카오 로그인 API",
)
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "200",
            description = "성공 응답",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiResponse::class),
                    examples = [
                        ExampleObject(
                            name = "카카오 로그인 성공",
                            value = """
                                {
                                  "code": "0000",
                                  "message": "SUCCESS",
                                  "data": {
                                    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
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
annotation class KakaoLoginSwagger
