package org.misarch.user.service

import kotlinx.coroutines.reactor.awaitSingle
import net.sf.jsqlparser.statement.select.Offset
import org.misarch.user.event.EventPublisher
import org.misarch.user.event.UserEvents
import org.misarch.user.event.model.CreateUserDTO
import org.misarch.user.graphql.ifPresent
import org.misarch.user.graphql.input.UpdateUserInput
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
        repository.createUser(
            id = createUserDTO.id,
            username = createUserDTO.username,
            firstName = createUserDTO.firstName,
            lastName = createUserDTO.lastName,
            dateJoined = OffsetDateTime.now()
        )
        val savedUser = repository.findById(createUserDTO.id).awaitSingle()
        eventPublisher.publishEvent(UserEvents.USER_CREATED, savedUser.toEventDTO())
        return savedUser
    }

    /**
     * Updates a user
     *
     * @param input the input for the update
     * @return the updated user
     */
    suspend fun updateUser(input: UpdateUserInput): UserEntity {
        val user = repository.findById(input.id).awaitSingle()
        input.firstName?.let { user.firstName = it }
        input.lastName?.let { user.lastName = it }
        input.birthday.ifPresent { user.birthday = it }
        input.gender.ifPresent { user.gender = it }
        return repository.save(user).awaitSingle()
    }

}