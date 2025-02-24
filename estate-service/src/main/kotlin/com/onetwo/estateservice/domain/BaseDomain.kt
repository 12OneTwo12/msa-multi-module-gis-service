package com.onetwo.estateservice.domain

import com.onetwo.estateservice.adapter.out.persistence.entity.BaseEntity
import com.onetwo.estateservice.common.GlobalStatus
import java.time.Instant

open class BaseDomain(
    var state: Boolean,
    var createdAt: Instant,
    var createdBy: String,
    var updatedAt: Instant? = null,
    var updatedBy: String? = null,
) {

    protected fun setMetaDataByEntity(entity: BaseEntity) {
        this.createdBy = entity.createBy
        this.createdAt = entity.createdAt
        this.updatedBy = entity.updatedBy
        this.updatedAt = entity.updatedAt
        this.state = entity.state
    }

    fun delete(requestUserId: String = this.createdBy) {
        this.state = GlobalStatus.PERSISTENCE_DELETED
        this.updatedAt = Instant.now()
        this.updatedBy = this.createdBy
    }

    val isDeleted: Boolean
        get() = state
}