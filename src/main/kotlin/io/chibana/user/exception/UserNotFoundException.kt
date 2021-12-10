package io.chibana.user.exception

class UserNotFoundException(userId: Long) : Exception(ERROR_MESSAGE.format(userId)) {

    companion object {
        private const val ERROR_MESSAGE = "User Id not found. Id=%s"
    }
}
