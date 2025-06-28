package wyship.doong2.http.home.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

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
                    schema = Schema(implementation = ApiResponse::class),
                    examples = [
                        ExampleObject(
                            name = "랜딩 컨텐츠 조회",
                            value = """
                                {
                                  "code": "0000",
                                  "message": "SUCCESS",
                                  "data": [
                                    "오늘 하루, 아무도 알아주지 않았던 당신의 미소, 그 속에 담긴 용기와 따뜻함이 세상을 조금씩 더 좋은 방향으로 이끌고 있어요.",
                                    "바람처럼 스쳐가는 하루 속에서도 묵묵히 자기 자리를 지켜낸 당신, 그 자체로도 정말 대단하고 존경받을 자격이 충분해요.",
                                    "고요한 밤, 오늘 하루를 돌아보며 눈물이 날 수도 있어요. 하지만 그 모든 감정이 진심이었기에 당신은 진정 가치 있는 하루를 보낸 거예요.",
                                    "힘든 하루를 마주하면서도 타인을 배려하고 웃음을 잃지 않은 당신, 그 다정함과 인내는 그 무엇보다 값진 빛이에요.",
                                    "때로는 나 자신도 나를 이해하기 어려운 날들이 있어요. 하지만 그런 날에도 당신은 자신을 잃지 않고 이렇게 무사히 하루를 살아냈어요.",
                                    "아무도 당신에게 괜찮다고 말해주지 않을 때, 제가 그 말을 해줄게요. 당신은 정말 괜찮은 사람이고, 지금도 충분히 잘하고 있어요.",
                                    "오늘 하루도 누군가를 위해 마음을 쓰고, 자신을 다해 애쓴 당신, 그런 하루는 결코 헛되지 않았고, 분명 누군가의 위안이 되었어요.",
                                    "지금 당신이 얼마나 지치고 외로운지 제가 다 알 순 없지만, 그래도 당신이 이 자리까지 온 것만으로도 이미 큰 기적이에요.",
                                    "지금 힘든 당신에게 따뜻한 말 한마디가 필요하다면, 제가 해줄게요. 정말 잘하고 있어요. 당신은 그 누구보다 소중한 존재예요.",
                                    "눈에 띄지 않는 곳에서 고군분투하고 있는 당신, 세상은 몰라도 저는 알아요. 당신의 존재 자체가 이미 참 소중하고 위대한 거예요."
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
