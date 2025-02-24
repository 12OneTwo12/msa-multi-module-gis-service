package com.onetwo.estateservice.application.port.`in`.response

import com.onetwo.estateservice.domain.Estate

data class EstateDetail(
    private val estate: Estate
) {
    val estateId: Long? = estate.id
    val place: String = estate.place
    val x: Double = estate.point.x
    val y: Double = estate.point.y
}
