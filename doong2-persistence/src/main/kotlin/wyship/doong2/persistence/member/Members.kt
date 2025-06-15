package wyship.doong2.persistence.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import wyship.doong2.persistence.base.BaseTimeEntity

@Entity
@Table(name = "members")
data class Members(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val nickname: String,
    @Column(nullable = false)
    val tokenId: String,
) : BaseTimeEntity()
