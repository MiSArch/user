package org.misarch.user.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.user.persistence.model.UserEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.*

/**
 * Repository for [UserEntity]s
 */
@Repository
interface UserRepository : QuerydslR2dbcRepository<UserEntity, UUID> {

    @Modifying
    @Query("INSERT INTO UserEntity (id, firstName, lastName, username, dateJoined) VALUES (:id, :firstName, :lastName, :username, :dateJoined)")
    suspend fun createUser(
        @Param("id") id: UUID,
        @Param("firstName") firstName: String,
        @Param("lastName") lastName: String,
        @Param("username") username: String,
        @Param("dateJoined") dateJoined: OffsetDateTime
    )

}