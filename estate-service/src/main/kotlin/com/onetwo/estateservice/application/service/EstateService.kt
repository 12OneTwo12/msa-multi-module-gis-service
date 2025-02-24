package com.onetwo.estateservice.application.service

import com.onetwo.estateservice.application.port.`in`.command.GetEstatesByCoordinateCommand
import com.onetwo.estateservice.application.port.`in`.response.EstateDetail
import com.onetwo.estateservice.application.port.`in`.usecase.ReadEstateUseCase
import com.onetwo.estateservice.application.port.out.ReadEstatePort
import com.onetwo.estateservice.domain.Estate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EstateService(
    private val readEstatePort: ReadEstatePort
) : ReadEstateUseCase {

    /**
     * @inheritDoc
     */
    @Transactional(readOnly = true)
    override fun getEstatesByCoordinate(command: GetEstatesByCoordinateCommand): List<EstateDetail> {
        val estateList: List<Estate> = readEstatePort.getEstatesByPolygon(command)

        return estateList.map(::EstateDetail)
    }
}