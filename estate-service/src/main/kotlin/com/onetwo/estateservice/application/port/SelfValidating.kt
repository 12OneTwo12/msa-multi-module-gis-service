package com.onetwo.estateservice.application.port

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator

abstract class SelfValidating<T> protected constructor() {
    private val validator: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        this.validator = factory.validator
    }

    protected fun validateSelf() {
        val violations = validator.validate(this as T)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}