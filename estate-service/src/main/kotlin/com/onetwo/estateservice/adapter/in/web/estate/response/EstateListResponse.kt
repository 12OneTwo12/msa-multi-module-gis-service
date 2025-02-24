package com.onetwo.estateservice.adapter.`in`.web.estate.response

import com.onetwo.estateservice.application.port.`in`.response.EstateDetail

data class EstateListResponse(
    val estates: List<EstateDetail>
)
