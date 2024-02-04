package org.misarch.user.graphql

import com.expediagroup.graphql.generator.federation.FederatedSchemaGeneratorHooks
import com.expediagroup.graphql.generator.federation.execution.FederatedTypeResolver
import graphql.scalars.datetime.DateScalar
import graphql.scalars.datetime.DateTimeScalar
import graphql.scalars.id.UUIDScalar
import graphql.schema.GraphQLSchema
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*
import kotlin.reflect.KType

@Configuration
class GraphQLConfiguration {

    @Bean
    fun federatedSchemaGeneratorHooks(resolvers: List<FederatedTypeResolver>) =
        object : FederatedSchemaGeneratorHooks(resolvers) {
            override fun willGenerateGraphQLType(type: KType): GraphQLType? {
                return when (type.classifier) {
                    OffsetDateTime::class -> DateTimeScalar.INSTANCE
                    LocalDate::class -> DateScalar.INSTANCE
                    UUID::class -> UUIDScalar.INSTANCE
                    else -> null
                }
            }

            override fun didBuildSchema(builder: GraphQLSchema.Builder): GraphQLSchema.Builder {
                return super.didBuildSchema(builder).clearDirectives()
            }
        }

}