package wyship.doong2.core.letter.port

import wyship.doong2.core.exception.CommonException
import java.time.LocalDate

interface LetterSavePort {
    fun saveLetter(command: SaveLetterCommand): Result<SaveLetterResult>

    data class SaveLetterCommand(
        val senderId: Long,
        val receiverId: Long,
        val content: String,
        val scheduleDate: LocalDate,
        val decorationId: Long,
    )

    data class SaveLetterResult(
        val letterId: Long,
    )

    class LetterSaveFailException : CommonException()
}
