package com.onetwo.estateservice.application.port

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator

abstract class SelfValidating<T>(private val clazz: Class<T>) {
    private val validator: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        this.validator = factory.validator
    }

    protected fun validateSelf() {
        when {
            !clazz.isInstance(this) -> {
                throw IllegalArgumentException("Invalid type: expected ${clazz.simpleName}, but got ${this::class.simpleName}")
            }

            else -> {
                val violations = validator.validate(clazz.cast(this))
                if (violations.isNotEmpty()) {
                    throw ConstraintViolationException(violations)
                }
            }
        }
    }
}
