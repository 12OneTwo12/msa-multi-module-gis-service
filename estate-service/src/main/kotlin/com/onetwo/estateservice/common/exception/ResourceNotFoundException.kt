package com.onetwo.estateservice.common.exception

class ResourceNotFoundException(override val message: String) : RuntimeException(message) {
}