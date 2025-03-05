package com.onetwo.reviewservice.service

import com.onetwo.reviewservice.dto.ReviewDto
import com.onetwo.reviewservice.jooq.generated.tables.Review
import com.onetwo.reviewservice.jooq.generated.tables.records.ReviewRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewServiceImpl(
    private val dsl: DSLContext
) : ReviewService {

    @Transactional(readOnly = true)
    override fun getReviewDetail(reviewId: Long): ReviewDto {
        val review: ReviewRecord = dsl.selectFrom(Review.REVIEW)
            .where(Review.REVIEW.ID.eq(reviewId))
            .fetchOne()
            ?: throw NoSuchElementException("리뷰 ID $reviewId 를 찾을 수 없습니다.")

        return ReviewDto(review)
    }
}