package com.onetwo.estateservice.common.exception

class ResourceAlreadyExistException(override val message: String) : RuntimeException(message) {
}