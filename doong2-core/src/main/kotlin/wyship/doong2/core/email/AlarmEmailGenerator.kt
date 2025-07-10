package wyship.doong2.core.email

import wyship.doong2.core.letter.port.Letter
import wyship.doong2.core.member.port.Member

object AlarmEmailGenerator {
    private const val DEFAULT_TITLE = "오늘 도착한 편지가 있습니다!"
    private const val DEFAULT_BODY = "오늘 도착한 편지가 있습니다. 서비스에 접속해 확인해보세요!"

    fun generate(receiver: Member, letter: Letter): AlarmEmail =
        AlarmEmail(
            receiverEmail = receiver.email,
            title = DEFAULT_TITLE,
            content = DEFAULT_BODY,
        )

    fun generateAll(letters: List<Letter>, memberResolver: (Long) -> Member?): List<AlarmEmail> {
        val emails = mutableListOf<AlarmEmail>()
        for (letter in letters) {
            val receiver = memberResolver(letter.receiverId)
            if (receiver != null) {
                emails.add(generate(receiver, letter))
            }
        }
        return emails
    }
}
