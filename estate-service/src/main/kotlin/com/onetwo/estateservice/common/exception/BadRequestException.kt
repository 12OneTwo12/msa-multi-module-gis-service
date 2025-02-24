package com.onetwo.estateservice.common.exception

class BadRequestException(override val message: String) : RuntimeException(message) {
}