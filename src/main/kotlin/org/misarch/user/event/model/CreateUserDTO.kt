package org.misarch.user.event.model

/**
 * User DTO used for events
 *
 * @property username the username of the user
 * @property name the name of the user
 */
data class CreateUserDTO(
    val username: String,
    val name: String
)