package io.chibana.user.controller

import io.chibana.user.exception.UserNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(basePackageClasses = [UserController::class])
class UserControllerAdvice {

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun badRequestHandler(ex: Exception): ResponseEntity<String> {
        return ResponseEntity
            .badRequest()
            .body(ex.message)
    }
}
