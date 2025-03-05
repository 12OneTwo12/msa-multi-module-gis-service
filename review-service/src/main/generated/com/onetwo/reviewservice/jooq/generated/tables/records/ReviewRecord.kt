/*
 * This file is generated by jOOQ.
 */
package com.onetwo.reviewservice.jooq.generated.tables.records


import com.onetwo.reviewservice.jooq.generated.tables.Review

import java.time.LocalDateTime

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("warnings")
open class ReviewRecord() : UpdatableRecordImpl<ReviewRecord>(Review.REVIEW) {

    open var id: Long?
        set(value): Unit = set(0, value)
        get(): Long? = get(0) as Long?

    open var userId: Long?
        set(value): Unit = set(1, value)
        get(): Long? = get(1) as Long?

    open var content: String?
        set(value): Unit = set(2, value)
        get(): String? = get(2) as String?

    open var rating: Int?
        set(value): Unit = set(3, value)
        get(): Int? = get(3) as Int?

    open var createdAt: LocalDateTime?
        set(value): Unit = set(4, value)
        get(): LocalDateTime? = get(4) as LocalDateTime?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Long?> = super.key() as Record1<Long?>

    /**
     * Create a detached, initialised ReviewRecord
     */
    constructor(id: Long? = null, userId: Long? = null, content: String? = null, rating: Int? = null, createdAt: LocalDateTime? = null): this() {
        this.id = id
        this.userId = userId
        this.content = content
        this.rating = rating
        this.createdAt = createdAt
        resetTouchedOnNotNull()
    }

    /**
     * Create a detached, initialised ReviewRecord
     */
    constructor(value: com.onetwo.reviewservice.jooq.generated.tables.pojos.Review?): this() {
        if (value != null) {
            this.id = value.id
            this.userId = value.userId
            this.content = value.content
            this.rating = value.rating
            this.createdAt = value.createdAt
            resetTouchedOnNotNull()
        }
    }
}
