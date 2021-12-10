package io.chibana.user.repository

import io.chibana.user.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>
