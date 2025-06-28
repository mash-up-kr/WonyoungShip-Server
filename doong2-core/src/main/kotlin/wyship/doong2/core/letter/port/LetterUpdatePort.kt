package wyship.doong2.core.letter.port

import wyship.doong2.core.exception.CommonException

interface LetterUpdatePort {
    fun updateLetter(command: LetterUpdateCommand): Result<Letter>

    data class LetterUpdateCommand(
        val letterId: Long,
        val viewed: Boolean,
        val marked: Boolean,
    ) {
        companion object {
            fun from(letter: Letter): LetterUpdateCommand = LetterUpdateCommand(
                letterId = letter.id,
                viewed = letter.viewed,
                marked = letter.marked,
            )
        }
    }

    class LetterUpdateFailException : CommonException()
}
