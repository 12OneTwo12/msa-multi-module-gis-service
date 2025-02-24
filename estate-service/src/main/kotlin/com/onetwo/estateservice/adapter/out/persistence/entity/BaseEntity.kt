package com.onetwo.estateservice.adapter.out.persistence.entity

import com.onetwo.estateservice.adapter.out.persistence.converter.BooleanNumberConverter
import com.onetwo.estateservice.common.GlobalStatus
import com.onetwo.estateservice.domain.BaseDomain
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
class BaseEntity(
    @Convert(converter = BooleanNumberConverter::class)
    var state: Boolean,
    @CreatedDate
    @Column(updatable = false)
    @Convert(converter = Jsr310JpaConverters.InstantConverter::class)
    var createdAt: Instant,
    @Column(nullable = false) var createBy: String,
    @LastModifiedDate
    @Convert(converter = Jsr310JpaConverters.InstantConverter::class)
    var updatedAt: Instant? = null,
    @Column
    var updatedBy: String? = null
) {

    protected fun setMetaDataByDomain(domain: BaseDomain) {
        this.createBy = domain.createdBy
        this.createdAt = domain.createdAt
        this.updatedBy = domain.updatedBy
        this.updatedAt = domain.updatedAt
        this.state = domain.state
    }

    fun delete() {
        this.state = GlobalStatus.PERSISTENCE_DELETED
        this.updatedAt = Instant.now()
        this.updatedBy = this.createBy
    }

    val isDeleted: Boolean
        get() = state
}