package com.onetwo.reviewservice.controller

import com.onetwo.reviewservice.common.GlobalURI
import com.onetwo.reviewservice.dto.ReviewDto
import com.onetwo.reviewservice.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewController(
    private val reviewService: ReviewService
) {

    @GetMapping(GlobalURI.REVIEW_ROOT + "/{review-id}")
    fun getReviewDetail(@PathVariable("review-id") reviewId: Long): ResponseEntity<ReviewDto> {
        return ResponseEntity.ok(reviewService.getReviewDetail(reviewId))
    }
}