package com.onetwo.estateservice.adapter.out.persistence

import com.onetwo.estateservice.adapter.out.persistence.entity.EstateEntity
import com.onetwo.estateservice.adapter.out.persistence.repository.EstateRepository
import com.onetwo.estateservice.application.port.`in`.command.GetEstatesByCoordinateCommand
import com.onetwo.estateservice.application.port.out.ReadEstatePort
import com.onetwo.estateservice.common.GeomUtil
import com.onetwo.estateservice.domain.Estate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EstatePersistenceAdapter(
    private val estateRepository: EstateRepository
) : ReadEstatePort {

    /**
     * @inheritDoc
     */
    @Transactional(readOnly = true)
    override fun getEstatesByPolygon(command: GetEstatesByCoordinateCommand): List<Estate> {
        val polygon = GeomUtil.createPolygonWKT(command.minX, command.minY, command.maxX, command.maxY);

        val estateEntityList: List<EstateEntity> =
            estateRepository.findByPolygon(polygon)

        return estateEntityList.map(Estate::entityToDomain)
    }
}