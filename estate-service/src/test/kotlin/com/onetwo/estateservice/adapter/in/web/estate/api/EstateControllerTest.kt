package com.onetwo.estateservice.adapter.`in`.web.estate.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.onetwo.estateservice.adapter.`in`.web.common.GlobalURI
import com.onetwo.estateservice.adapter.`in`.web.estate.request.GetEstatesByCoordinateRequest
import com.onetwo.estateservice.adapter.out.persistence.entity.EstateEntity
import com.onetwo.estateservice.adapter.out.persistence.repository.EstateRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("[통합] Estate Controller 테스트")
class EstateControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var estateRepository: EstateRepository

    @BeforeEach
    fun setUp() {
        val geometryFactory = GeometryFactory(PrecisionModel(), 4326)

        val estate1 = EstateEntity(
            place = "Test Place 1",
            point = geometryFactory.createPoint(Coordinate(127.0, 37.6)),
            state = true,
            createUser = "admin"
        )

        val estate2 = EstateEntity(
            place = "Test Place 2",
            point = geometryFactory.createPoint(Coordinate(126.95, 37.55)),
            state = true,
            createUser = "admin"
        )

        estateRepository.saveAll(listOf(estate1, estate2))
    }

    @Test
    @DisplayName("estate 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get estate list by coordinate`() {
        //given
        val requestParam = GetEstatesByCoordinateRequest(126.9, 37.5, 127.1, 37.7)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("minX", requestParam.minX)
            .queryParam("minY", requestParam.minY)
            .queryParam("maxX", requestParam.maxX)
            .queryParam("maxY", requestParam.maxY)
            .build()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.ESTATE_RECORD_ROOT + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}