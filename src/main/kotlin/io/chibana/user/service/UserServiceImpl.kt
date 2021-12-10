package io.chibana.user.service

import io.chibana.user.exception.UserNotFoundException
import io.chibana.user.model.User
import io.chibana.user.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    @Throws(UserNotFoundException::class)
    override fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException(userId) }
    }

    override fun getAll(): List<User> {
        return userRepository.findAll() as List<User>
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }
}
