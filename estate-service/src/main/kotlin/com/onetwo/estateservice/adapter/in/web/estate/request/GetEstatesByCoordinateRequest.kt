package com.onetwo.estateservice.adapter.`in`.web.estate.request

import jakarta.validation.constraints.NotNull

data class GetEstatesByCoordinateRequest(
    @field:NotNull(message = "minX should have value") val minX: Double,
    @field:NotNull(message = "minY should have value") val minY: Double,
    @field:NotNull(message = "maxX should have value") val maxX: Double,
    @field:NotNull(message = "maxY should have value") val maxY: Double
)
