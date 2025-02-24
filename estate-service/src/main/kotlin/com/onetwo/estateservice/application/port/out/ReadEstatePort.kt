package com.onetwo.estateservice.application.port.out

import com.onetwo.estateservice.application.port.`in`.command.GetEstatesByCoordinateCommand
import com.onetwo.estateservice.domain.Estate

interface ReadEstatePort {

    /**
     * Get estate list by polygon on persistence
     *
     * @param command min x,y and max x,u coordinate
     * @return Estate list
     */
    fun getEstatesByPolygon(command: GetEstatesByCoordinateCommand): List<Estate>
}