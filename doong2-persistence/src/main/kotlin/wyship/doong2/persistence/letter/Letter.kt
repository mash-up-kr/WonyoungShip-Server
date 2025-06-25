package wyship.doong2.persistence.letter

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import wyship.doong2.persistence.base.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "letters")
data class Letter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val senderMemberId: Long,
    @Column(nullable = false)
    val receiverMemberId: Long,
    @Column(nullable = false)
    val messageContent: String,
    @Column(nullable = false)
    val scheduleDate: LocalDate,
    @Column
    val decorationId: Long,
) : BaseTimeEntity()

enum class WeatherType {
    SUNNY, CLOUDY, RAINY, SNOWY, NIGHT_SHINING
}
