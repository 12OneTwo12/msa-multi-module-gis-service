package com.onetwo.reviewservice.service

import com.onetwo.reviewservice.dto.ReviewDto

interface ReviewService {
    fun getReviewDetail(reviewId: Long): ReviewDto
}