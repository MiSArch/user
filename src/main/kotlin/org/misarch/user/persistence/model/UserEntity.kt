package org.misarch.user.persistence.model

import org.misarch.user.event.model.UserDTO
import org.misarch.user.graphql.model.Gender
import org.misarch.user.graphql.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * User entity
 *
 * @property username the username of the user
 * @property name the name of the user
 * @property birthday the birthday of the user
 * @property gender the gender of the user
 * @property dateJoined the date the user joined
 * @property id the id of the user
 */
@Table
class UserEntity(
    var username: String,
    var firstName: String,
    var lastName: String,
    var birthday: LocalDate?,
    var gender: Gender?,
    val dateJoined: OffsetDateTime,
    @Id
    override val id: UUID? = null
) : BaseEntity<User> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QUserEntity.userEntity!!
    }

    override fun toDTO(): User {
        return User(
            id = id!!,
            username = username,
            firstName = firstName,
            lastName = lastName,
            birthday = birthday,
            gender = gender,
            dateJoined = dateJoined
        )
    }

    fun toEventDTO(): UserDTO {
        return UserDTO(
            id = id!!,
            username = username,
            firstName = firstName,
            lastName = lastName,
            dateJoined = dateJoined.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        )
    }

}