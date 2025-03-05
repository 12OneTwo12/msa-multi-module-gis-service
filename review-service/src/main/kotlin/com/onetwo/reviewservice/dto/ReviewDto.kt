package com.onetwo.reviewservice.dto

import com.onetwo.reviewservice.jooq.generated.tables.records.ReviewRecord

data class ReviewDto(
    private val reviewRecode: ReviewRecord
) {
    val reviewId = reviewRecode.id
    val userId = reviewRecode.userId
    val content = reviewRecode.content
}
