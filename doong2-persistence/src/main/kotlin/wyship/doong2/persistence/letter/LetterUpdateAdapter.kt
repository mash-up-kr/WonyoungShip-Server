package wyship.doong2.persistence.letter

import org.springframework.stereotype.Component
import wyship.doong2.core.letter.port.Letter
import wyship.doong2.core.letter.port.LetterUpdatePort

@Component
class LetterUpdateAdapter(
    private val letterRepository: LetterRepository,
) : LetterUpdatePort {

    override fun updateLetter(command: LetterUpdatePort.LetterUpdateCommand): Result<Letter> =
        runCatching {
            return@runCatching letterRepository.findById(command.letterId)
                .map { entity ->
                    entity.viewed = command.viewed
                    entity.marked = command.marked
                    entity.toDomain()
                }
                .orElseThrow { throw IllegalArgumentException("No letter found with id ${command.letterId}") }()
        }
}
