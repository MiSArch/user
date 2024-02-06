package org.misarch.user.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.user.graphql.dataloader.UserDataLoader
import org.misarch.user.graphql.model.User
import org.misarch.user.graphql.model.connection.UserConnection
import org.misarch.user.graphql.model.connection.UserOrder
import org.misarch.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Defines GraphQL queries
 *
 * @property userRepository repository for users
 */
@Component
class Query(
    private val userRepository: UserRepository,
) : Query {

    @GraphQLDescription("Get a user by id")
    fun user(
        @GraphQLDescription("The id of the user")
        id: UUID,
        dfe: DataFetchingEnvironment
    ): CompletableFuture<User> {
        val authorizedUser = dfe.authorizedUser
        if (!authorizedUser.isEmpolyee && authorizedUser.id != id) {
            throw IllegalStateException("Unauthorized access: ${authorizedUser.id} is not an employee or admin")
        }
        return dfe.getDataLoader<UUID, User>(UserDataLoader::class.simpleName!!).load(id)
    }

    @GraphQLDescription("Get all users")
    suspend fun users(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: UserOrder? = null,
        dfe: DataFetchingEnvironment
    ): UserConnection {
        val authorizedUser = dfe.authorizedUser
        if (!authorizedUser.isEmpolyee) {
            throw IllegalStateException("Unauthorized access: ${authorizedUser.id} is not an employee or admin")
        }
        return UserConnection(first, skip, null, orderBy, userRepository)
    }

    @GraphQLDescription("Get the currently authenticated user")
    suspend fun currentUser(
        dfe: DataFetchingEnvironment
    ): User? {
        return dfe.authorizedUserOrNull?.id?.let {
            userRepository.findById(it).awaitSingle().toDTO()
        }
    }

}