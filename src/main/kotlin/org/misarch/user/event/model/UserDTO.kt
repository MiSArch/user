package org.misarch.user.event.model

import java.util.*

/**
 * User DTO used for events
 *
 * @property id id of the user
 * @property username the username of the user
 * @property name the name of the user
 */
data class UserDTO(
    val id: UUID,
    val username: String,
    val name: String,
    val dateJoined: String
)