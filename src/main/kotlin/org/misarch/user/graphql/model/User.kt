package org.misarch.user.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@GraphQLDescription("A user.")
@KeyDirective(fields = FieldSet("id"))
class User(
    id: UUID,
    @property:GraphQLDescription("The username of the user")
    val username: String,
    @property:GraphQLDescription("The name of the user")
    val name: String,
    @property:GraphQLDescription("The birthday of the user")
    val birthday: LocalDate?,
    @property:GraphQLDescription("The gender of the user")
    val gender: Gender?,
    @property:GraphQLDescription("The date the user joined")
    val dateJoined: OffsetDateTime,
) : Node(id)