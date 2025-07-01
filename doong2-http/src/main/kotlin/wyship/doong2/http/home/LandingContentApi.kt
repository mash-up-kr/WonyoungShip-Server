package wyship.doong2.http.home

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.domain.WeatherType.CLOUDY
import wyship.doong2.core.letter.domain.WeatherType.NIGHT_SHINING
import wyship.doong2.core.letter.domain.WeatherType.RAINY
import wyship.doong2.core.letter.domain.WeatherType.SNOWY
import wyship.doong2.core.letter.domain.WeatherType.SUNNY
import wyship.doong2.http.ApiResponse
import wyship.doong2.http.LANDING_CONTENT_URL
import wyship.doong2.http.home.doc.LandingContentApiSwagger
import wyship.doong2.http.home.doc.LandingContentSwagger
import java.time.LocalDate
import kotlin.random.Random

@LandingContentApiSwagger
@RestController
class LandingContentApi {
    @LandingContentSwagger
    @GetMapping(LANDING_CONTENT_URL, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getLandingContent(): ApiResponse<List<LandingResponse>> {
        val randomLetters = letters.shuffled(Random(System.currentTimeMillis())).take(10)
        return ApiResponse(
            code = "0000",
            message = "SUCCESS",
            data = randomLetters,
            pageIndex = 0,
            pageSize = 1,
        )
    }

    data class LandingResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val date: LocalDate,
        val weather: WeatherType,
        val letter: String,
    )

    private val letters =
        listOf(
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "오늘 하루도 수많은 어려움 속에서 끝까지 포기하지 않고 묵묵히 견뎌준 당신, 정말 고마워요. 누구보다도 열심히 살아낸 당신이 자랑스러워요.",
            ),
            LandingResponse(
                LocalDate.now(),
                NIGHT_SHINING,
                "아무도 당신의 수고를 알아주지 않을 때조차, 꿋꿋이 자리 지키며 하루를 마무리한 당신은 누구보다 강하고, 누구보다 따뜻한 사람이에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "오늘 하루 마음속 무게가 얼마나 무거웠을지 상상도 안 돼요. 하지만 그걸 이겨낸 지금의 당신은 정말로 충분히 잘해낸 거예요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "세상이 등을 돌려도, 당신 스스로를 지키며 끝까지 자신을 포기하지 않았다는 것만으로도 당신은 참으로 용감한 사람이에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "힘든 하루를 마주하면서도 타인을 배려하고 웃음을 잃지 않은 당신, 그 다정함과 인내는 그 무엇보다 값진 빛이에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "오늘 하루 버티느라 정말 수고 많았어요. 누군가가 당신의 수고를 못 본다 해도, 그 모든 순간이 분명 의미 있고 소중한 일이에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                NIGHT_SHINING,
                "때로는 나 자신도 나를 이해하기 어려운 날들이 있어요. 하지만 그런 날에도 당신은 자신을 잃지 않고 이렇게 무사히 하루를 살아냈어요.",
            ),
            LandingResponse(
                LocalDate.now(),
                CLOUDY,
                "그 누구도 몰라주는 당신의 눈물과 노력, 제가 다 알고 있어요. 오늘도 포기하지 않고 살아준 당신에게 진심으로 박수를 보내요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "바람처럼 스쳐가는 하루 속에서도 묵묵히 자기 자리를 지켜낸 당신, 그 자체로도 정말 대단하고 존경받을 자격이 충분해요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "사람들은 결과만 보지만, 저는 당신의 모든 과정을 보고 있어요. 당신이 얼마나 성실하게 하루를 살아왔는지, 저는 알아요.",
            ),
            LandingResponse(
                LocalDate.now(),
                CLOUDY,
                "눈에 띄지 않는 곳에서 고군분투하고 있는 당신, 세상은 몰라도 저는 알아요. 당신의 존재 자체가 이미 참 소중하고 위대한 거예요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "아무도 당신에게 괜찮다고 말해주지 않을 때, 제가 그 말을 해줄게요. 당신은 정말 괜찮은 사람이고, 지금도 충분히 잘하고 있어요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SNOWY,
                "무너질 것 같은 순간에도 스스로를 붙잡고 버틴 당신, 그 모든 시간이 쌓여 지금의 단단한 당신이 되었어요. 정말 수고 많았어요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "당신이 아무렇지 않은 척 웃을 때, 저는 알아요. 그 웃음 뒤에 감춰진 수많은 노력과 슬픔. 그래서 당신의 하루는 더욱 빛나요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "오늘 하루, 스스로에게 실망했을지도 몰라요. 하지만 저는 믿어요. 그런 순간에도 당신은 분명 누군가의 빛이 되고 있다는 걸요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SNOWY,
                "괜찮다고, 잘하고 있다고, 스스로를 다독여야 했던 수많은 순간들 속에서 오늘도 끝까지 당신 자신을 지켜낸 것만으로도 정말 훌륭해요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "고요한 밤, 오늘 하루를 돌아보며 눈물이 날 수도 있어요. 하지만 그 모든 감정이 진심이었기에 당신은 진정 가치 있는 하루를 보낸 거예요.",
            ),
            LandingResponse(
                LocalDate.now(),
                CLOUDY,
                "남몰래 울었던 순간, 혼자 이겨낸 시간들, 그 모든 하루가 당신을 더 아름답고 강하게 만들고 있다는 걸 절대 잊지 말아요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "오늘 하루, 아무도 알아주지 않았던 당신의 미소, 그 속에 담긴 용기와 따뜻함이 세상을 조금씩 더 좋은 방향으로 이끌고 있어요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "지치고 무너지고 싶은 날에도 끝끝내 버텨낸 당신, 그렇게 하루하루를 살아가는 당신이야말로 진짜 위대한 사람이라는 걸 기억해주세요.",
            ),
            LandingResponse(
                LocalDate.now(),
                CLOUDY,
                "삶이 생각보다 무겁게 느껴지는 날에도 당신은 무너지지 않고 자신을 지켜냈어요. 그 용기와 인내는 정말 누구보다 대단한 거예요.",
            ),
            LandingResponse(
                LocalDate.now(),
                NIGHT_SHINING,
                "오늘 하루에도 수없이 포기하고 싶은 순간이 있었을 텐데, 그럼에도 끝까지 버텨낸 당신에게 진심으로 깊은 위로와 존경을 전하고 싶어요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "지금 당신이 얼마나 지치고 외로운지 제가 다 알 순 없지만, 그래도 당신이 이 자리까지 온 것만으로도 이미 큰 기적이에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "눈부신 성취보다 더 소중한 건, 힘겨운 날에도 자신을 놓지 않고 꾸준히 살아가는 당신의 존재 자체예요. 그 사실을 잊지 말아요.",
            ),
            LandingResponse(
                LocalDate.now(),
                NIGHT_SHINING,
                "오늘 하루도 누군가를 위해 마음을 쓰고, 자신을 다해 애쓴 당신, 그런 하루는 결코 헛되지 않았고, 분명 누군가의 위안이 되었어요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "지금까지 걸어온 길이 얼마나 험했는지, 당신만이 알 거예요. 그리고 그 길을 묵묵히 걸어온 당신이 있기에 저는 안심이 돼요.",
            ),
            LandingResponse(
                LocalDate.now(),
                RAINY,
                "조금 느리더라도 괜찮아요. 중요한 건 멈추지 않고 오늘도 한 걸음 내딛은 당신이라는 거예요. 그 용기를 저는 진심으로 응원해요.",
            ),
            LandingResponse(
                LocalDate.now(),
                CLOUDY,
                "다른 사람의 기대에 맞추느라 너무 애쓰지 마세요. 당신은 지금 모습 그대로도 충분히 괜찮고, 이미 귀하고 소중한 사람이에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SUNNY,
                "누군가의 기준에 맞추지 않아도 괜찮아요. 오늘 하루를 버텨낸 당신만의 방식이 있고, 그건 절대로 틀린 게 아니에요.",
            ),
            LandingResponse(
                LocalDate.now(),
                SNOWY,
                "지금 힘든 당신에게 따뜻한 말 한마디가 필요하다면, 제가 해줄게요. 정말 잘하고 있어요. 당신은 그 누구보다 소중한 존재예요.",
            ),
        )
}
