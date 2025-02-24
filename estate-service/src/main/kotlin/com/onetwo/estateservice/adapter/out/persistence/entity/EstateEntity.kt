package com.onetwo.estateservice.adapter.out.persistence.entity

import com.onetwo.estateservice.domain.Estate
import jakarta.persistence.*
import org.locationtech.jts.geom.Point
import java.time.Instant

@Entity
class EstateEntity(
    @Column(name = "place", nullable = false)
    val place: String,
    @Column(name = "point", columnDefinition = "GEOMETRY")
    var point: Point,
    state: Boolean,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(domain: Estate): EstateEntity {
            val estateEntity = EstateEntity(
                domain.place,
                domain.point,
                domain.state,
                domain.createdBy,
            )

            estateEntity.id = domain.id
            estateEntity.setMetaDataByDomain(domain)

            return estateEntity
        }
    }
}