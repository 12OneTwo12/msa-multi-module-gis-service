package com.onetwo.estateservice.domain

import com.onetwo.estateservice.adapter.out.persistence.entity.EstateEntity
import org.locationtech.jts.geom.Point
import java.time.Instant

class Estate(
    val id: Long?,
    val place: String,
    var point: Point,
    state: Boolean,
    createdBy: String
) : BaseDomain(state, createdAt = Instant.now(), createdBy) {

    companion object {
        fun entityToDomain(entity: EstateEntity): Estate {
            val estate = Estate(
                entity.id,
                entity.place,
                entity.point,
                entity.state,
                entity.createBy
            )

            estate.setMetaDataByEntity(entity)

            return estate
        }
    }
}