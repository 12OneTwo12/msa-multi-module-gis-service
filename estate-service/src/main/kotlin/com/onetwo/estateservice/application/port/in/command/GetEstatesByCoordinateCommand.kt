package com.onetwo.estateservice.application.port.`in`.command

import com.onetwo.estateservice.application.port.SelfValidating

data class GetEstatesByCoordinateCommand(
    val minX: Double,
    val minY: Double,
    val maxX: Double,
    val maxY: Double
) : SelfValidating<GetEstatesByCoordinateCommand>() {
    init {
        this.validateSelf()
    }
}
