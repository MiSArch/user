package org.misarch.user.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.user.event.EventPublisher
import org.misarch.user.event.UserEvents
import org.misarch.user.event.model.CreateUserDTO
import org.misarch.user.event.model.UserDTO
import org.misarch.user.persistence.model.UserEntity
import org.misarch.user.persistence.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

/**
 * Service for [UserEntity]s
 *
 * @param repository the repository for [UserEntity]s
 * @property eventPublisher publisher for events
 */
@Service
class UserService(
    repository: UserRepository,
    private val eventPublisher: EventPublisher
) : BaseService<UserEntity, UserRepository>(repository) {

    /**
     * Creates a new user
     *
     * @param createUserDTO defines the new user
     * @return the created user
     */
    suspend fun createUser(createUserDTO: CreateUserDTO): UserEntity {
        val user = UserEntity(createUserDTO.username, createUserDTO.name, null, null, OffsetDateTime.now())
        val savedUser = repository.save(user).awaitSingle()
        eventPublisher.publishEvent(UserEvents.USER_CREATED, savedUser.toEventDTO())
        return savedUser
    }

}