package org.misarch.user.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import org.misarch.user.event.model.CreateUserDTO
import org.misarch.user.graphql.model.User
import org.misarch.user.service.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * Defines GraphQL mutations
 *
 * @property userService used for handling users
 */
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
class Mutation(
    private val userService: UserService
) : Mutation {

    @GraphQLDescription("Create TEST user")
    suspend fun createTestUser(
        @GraphQLDescription("Definition of the Test user")
        input: CreateUserDTO
    ): User {
        return userService.createUser(input).toDTO()
    }
}