package io.chibana.user.controller

import io.chibana.user.exception.UserNotFoundException
import io.chibana.user.model.User
import io.chibana.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("/users")
class UserController(private val userServiceImpl: UserService) {

    @Throws(UserNotFoundException::class)
    @GetMapping("/{userId}")
    fun get(@PathVariable userId: Long): ResponseEntity<User> = userServiceImpl.getUserById(userId).let {
        ResponseEntity.ok(it)
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<User>> = userServiceImpl.getAll().let { ResponseEntity.ok(it) }

    @PostMapping
    fun create(@RequestBody user: User): ResponseEntity<User> = userServiceImpl.createUser(user).let {
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }
}
