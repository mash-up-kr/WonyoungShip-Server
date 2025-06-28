package wyship.doong2.persistence.letter

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import wyship.doong2.core.letter.domain.WeatherType
import wyship.doong2.core.letter.port.Letter
import wyship.doong2.persistence.base.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "letters")
data class LetterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = true)
    val senderMemberId: Long? = null,

    @Column(nullable = false)
    val receiverMemberId: Long,

    @Column(nullable = false)
    val messageContent: String,

    @Column(nullable = false)
    val scheduleDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val weather: WeatherType,

    @Column(nullable = true)
    val musicId: Long? = null,

    @Column(nullable = false, length = 15)
    val senderNickname: String,

    @Column(nullable = true)
    val fortuneCookieId: Long? = null,

    @Column(nullable = false)
    var viewed: Boolean = false,

    @Column(nullable = false)
    var marked: Boolean = false,
) : BaseTimeEntity() {

    fun toDomain(): Letter? = this.id?.let { id ->
        Letter(
            id = id,
            senderId = this.senderMemberId,
            senderNickname = this.senderNickname,
            receiverId = this.receiverMemberId,
            content = this.messageContent,
            weatherType = this.weather,
            musicId = this.musicId,
            scheduleDate = this.scheduleDate,
            fortuneCookieId = this.fortuneCookieId,
            createdAt = this.createdAt,
            viewed = this.viewed,
            marked = this.marked,
        )
    }
}
