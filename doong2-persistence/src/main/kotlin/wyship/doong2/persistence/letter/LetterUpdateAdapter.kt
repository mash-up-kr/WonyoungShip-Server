package wyship.doong2.persistence.letter

import org.springframework.stereotype.Component
import wyship.doong2.core.letter.port.Letter
import wyship.doong2.core.letter.port.LetterUpdatePort
import kotlin.jvm.optionals.getOrElse

@Component
class LetterUpdateAdapter(
    private val letterRepository: LetterRepository,
) : LetterUpdatePort {

    override fun updateLetter(command: LetterUpdatePort.LetterUpdateCommand): Result<Letter> =
        runCatching {
            val letter = letterRepository.findById(command.letterId)
                .getOrElse { throw IllegalArgumentException("No letter found with id ${command.letterId}") }

            letter.viewed = command.viewed
            letter.marked = command.marked

            return@runCatching requireNotNull(letter.toDomain()) {
                "Failed to map LetterEntity to domain. id=${command.letterId}"
            }
        }
}
