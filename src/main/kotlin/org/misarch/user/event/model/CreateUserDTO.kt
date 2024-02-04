package org.misarch.user.event.model

/**
 * User DTO used for events
 *
 * @property username the username of the user
 * @property firstName the first name of the user
 * @property lastName the last name of the user
 */
data class CreateUserDTO(
    val username: String,
    val firstName: String,
    val lastName: String
)