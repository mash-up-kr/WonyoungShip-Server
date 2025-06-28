package wyship.doong2.persistence.setting

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import wyship.doong2.persistence.base.BaseTimeEntity

@Entity
@Table(name = "service_properties")
class ServicePropertyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val serviceKey: String,
    @Column(nullable = false)
    val serviceValue: String,
) : BaseTimeEntity()
