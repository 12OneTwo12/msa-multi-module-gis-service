package com.onetwo.reviewservice.controller

import com.onetwo.reviewservice.common.GlobalURI
import com.onetwo.reviewservice.jooq.generated.tables.Review
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("[통합] Review Controller 테스트")
class ReviewControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var dsl: DSLContext

    @BeforeEach
    fun setup() {
        dsl.insertInto(Review.REVIEW)
            .set(Review.REVIEW.ID, 1L)
            .set(Review.REVIEW.USER_ID, 100L)
            .set(Review.REVIEW.CONTENT, "테스트 리뷰 내용")
            .set(Review.REVIEW.RATING, 5)
            .execute()
    }

    @Test
    @DisplayName("review 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get review detail`() {
        //given
        val reviewId = 1

        //when
        val resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get(GlobalURI.REVIEW_ROOT + "/" + reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }
}