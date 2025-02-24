package com.onetwo.estateservice.adapter.out.persistence.repository

import com.onetwo.estateservice.adapter.out.persistence.entity.EstateEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface EstateRepository : JpaRepository<EstateEntity, Long> {

    @Query(
        value = """
        SELECT * FROM estate_entity e
        WHERE ST_Contains(
            ST_GeomFromText(:polygonWKT, 4326),
            e.point
        )
    """, nativeQuery = true
    )
    fun findByPolygon(@Param("polygonWKT") polygon: String): List<EstateEntity>
}