package wyship.doong2.http.home.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import wyship.doong2.http.ApiResponse as CommonApiResponse

@Tag(name = "랜딩 컨텐츠 API")
annotation class LandingContentApiSwagger

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Operation(
    summary = "랜딩 컨텐츠 조회 API",
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
                            name = "랜딩 컨텐츠 조회",
                            value = """
                                {
                                  "code": "0000",
                                  "message": "SUCCESS",
                                  "data": [
                                    {
                                      "date": "2025-07-01",
                                      "weather": "CLOUDY",
                                      "letter": "그 누구도 몰라주는 당신의 눈물과 노력, 제가 다 알고 있어요. 오늘도 포기하지 않고 살아준 당신에게 진심으로 박수를 보내요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "NIGHT_SHINING",
                                      "letter": "오늘 하루에도 수없이 포기하고 싶은 순간이 있었을 텐데, 그럼에도 끝까지 버텨낸 당신에게 진심으로 깊은 위로와 존경을 전하고 싶어요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "CLOUDY",
                                      "letter": "눈에 띄지 않는 곳에서 고군분투하고 있는 당신, 세상은 몰라도 저는 알아요. 당신의 존재 자체가 이미 참 소중하고 위대한 거예요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "SNOWY",
                                      "letter": "무너질 것 같은 순간에도 스스로를 붙잡고 버틴 당신, 그 모든 시간이 쌓여 지금의 단단한 당신이 되었어요. 정말 수고 많았어요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "SUNNY",
                                      "letter": "당신이 아무렇지 않은 척 웃을 때, 저는 알아요. 그 웃음 뒤에 감춰진 수많은 노력과 슬픔. 그래서 당신의 하루는 더욱 빛나요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "SUNNY",
                                      "letter": "오늘 하루 버티느라 정말 수고 많았어요. 누군가가 당신의 수고를 못 본다 해도, 그 모든 순간이 분명 의미 있고 소중한 일이에요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "SUNNY",
                                      "letter": "오늘 하루, 아무도 알아주지 않았던 당신의 미소, 그 속에 담긴 용기와 따뜻함이 세상을 조금씩 더 좋은 방향으로 이끌고 있어요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "SUNNY",
                                      "letter": "아무도 당신에게 괜찮다고 말해주지 않을 때, 제가 그 말을 해줄게요. 당신은 정말 괜찮은 사람이고, 지금도 충분히 잘하고 있어요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "RAINY",
                                      "letter": "조금 느리더라도 괜찮아요. 중요한 건 멈추지 않고 오늘도 한 걸음 내딛은 당신이라는 거예요. 그 용기를 저는 진심으로 응원해요."
                                    },
                                    {
                                      "date": "2025-07-01",
                                      "weather": "SUNNY",
                                      "letter": "바람처럼 스쳐가는 하루 속에서도 묵묵히 자기 자리를 지켜낸 당신, 그 자체로도 정말 대단하고 존경받을 자격이 충분해요."
                                    }
                                  ],
                                  "pageIndex": 0,
                                  "pageSize": 1
                                }
                            """,
                        ),
                    ],
                ),
            ],
        ),
    ],
)
annotation class LandingContentSwagger
