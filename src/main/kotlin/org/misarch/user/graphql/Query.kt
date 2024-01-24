package org.misarch.user.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.misarch.user.graphql.dataloader.UserDataLoader
import org.misarch.user.graphql.model.User
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
        return dfe.getDataLoader<UUID, User>(UserDataLoader::class.simpleName!!).load(id)
    }

}