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
import wyship.doong2.persistence.base.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "letters")
data class Letter(
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
) : BaseTimeEntity()
