package org.misarch.user.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.user.graphql.AuthorizedUser
import org.misarch.user.graphql.model.User
import org.misarch.user.graphql.model.connection.base.*
import org.misarch.user.persistence.model.UserEntity
import org.misarch.user.persistence.repository.UserRepository

/**
 * A GraphQL connection for [User]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param authorizedUser The authorized user
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `User` values.")
@ShareableDirective
class UserConnection(
    first: Int?,
    skip: Int?,
    predicate: BooleanExpression?,
    order: UserOrder?,
    repository: UserRepository,
    authorizedUser: AuthorizedUser?,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<User, UserEntity>(
    first,
    skip,
    null,
    predicate,
    (order ?: UserOrder.DEFAULT).toOrderSpecifier(UserOrderField.ID),
    repository,
    UserEntity.ENTITY,
    authorizedUser,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = UserEntity.ENTITY.id
}

@GraphQLDescription("User order fields")
enum class UserOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order users by their id")
    ID(UserEntity.ENTITY.id),

    @GraphQLDescription("Order users by their username")
    USERNAME(UserEntity.ENTITY.username, UserEntity.ENTITY.id),
}

@GraphQLDescription("User order")
class UserOrder(
    direction: OrderDirection?, field: UserOrderField?
) : BaseOrder<UserOrderField>(direction, field) {

    companion object {
        val DEFAULT = UserOrder(OrderDirection.ASC, UserOrderField.ID)
    }
}