package com.onetwo.estateservice.adapter.`in`.web.estate.mapper

import com.onetwo.estateservice.adapter.`in`.web.estate.request.GetEstatesByCoordinateRequest
import com.onetwo.estateservice.adapter.`in`.web.estate.response.EstateListResponse
import com.onetwo.estateservice.application.port.`in`.command.GetEstatesByCoordinateCommand
import com.onetwo.estateservice.application.port.`in`.response.EstateDetail

class EstateDtoMapper {
    companion object {
        fun getEstatesByCoordinateRequestToCommand(request: GetEstatesByCoordinateRequest): GetEstatesByCoordinateCommand =
            GetEstatesByCoordinateCommand(request.minX, request.minY, request.maxX, request.maxY)

        fun estateDetailsToResponse(estateDetailList: List<EstateDetail>): EstateListResponse =
            EstateListResponse(estateDetailList)
    }
}