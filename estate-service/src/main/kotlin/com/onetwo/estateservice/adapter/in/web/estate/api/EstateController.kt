package com.onetwo.estateservice.adapter.`in`.web.estate.api

import com.onetwo.estateservice.adapter.`in`.web.common.GlobalURI
import com.onetwo.estateservice.adapter.`in`.web.estate.mapper.EstateDtoMapper
import com.onetwo.estateservice.adapter.`in`.web.estate.request.GetEstatesByCoordinateRequest
import com.onetwo.estateservice.adapter.`in`.web.estate.response.EstateListResponse
import com.onetwo.estateservice.application.port.`in`.usecase.ReadEstateUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class EstateController(
    private val readEstateUseCase: ReadEstateUseCase
) {

    /**
     * Get estate by coordinate inbound adapter
     *
     * @param request min x,y and max x,u coordinate
     * @return Estate detail list
     */
    @GetMapping(GlobalURI.ESTATE_RECORD_ROOT)
    fun getEstatesByCoordinate(@ModelAttribute @Valid request: GetEstatesByCoordinateRequest): ResponseEntity<EstateListResponse> {
        val getEstatesByCoordinateCommand = EstateDtoMapper.getEstatesByCoordinateRequestToCommand(request)
        val estateDetailList = readEstateUseCase.getEstatesByCoordinate(getEstatesByCoordinateCommand)
        val response = EstateDtoMapper.estateDetailsToResponse(estateDetailList)
        return ResponseEntity.ok(response)
    }
}