package wyship.doong2.persistence.fortunecookie

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

@Entity
@Table(name = "fortune_cookies")
data class FortuneCookieEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val weatherType: WeatherType,
    @Column(nullable = false)
    val text: String,
) : BaseTimeEntity()
