package com.onetwo.estateservice.application.port.`in`.usecase

import com.onetwo.estateservice.application.port.`in`.command.GetEstatesByCoordinateCommand
import com.onetwo.estateservice.application.port.`in`.response.EstateDetail

interface ReadEstateUseCase {

    /**
     * Get estate by coordinate use case
     *
     * @param command min x,y and max x,u coordinate
     * @return Estate detail list
     */
    fun getEstatesByCoordinate(command: GetEstatesByCoordinateCommand): List<EstateDetail>
}