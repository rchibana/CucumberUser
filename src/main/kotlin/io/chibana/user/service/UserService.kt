package io.chibana.user.service

import io.chibana.user.model.User

interface UserService {

    fun getUserById(userId: Long): User?

    fun getAll(): List<User>

    fun createUser(user: User): User
}
