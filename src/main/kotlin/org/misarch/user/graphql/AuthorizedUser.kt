package org.misarch.user.graphql

import java.util.UUID

/**
 * Represents an authorized user.
 *
 * @param id unique identifier of the user
 * @param roles list of roles assigned to the user
 */
data class AuthorizedUser(
    val id: UUID,
    val roles: List<String>
) {

    /**
     * Check if the user is an employee or admin.
     */
    val isEmpolyee: Boolean
        get() = roles.contains("EMPLOYEE") || isAdmin


    /**
     * Check if the user is an admin.
     */
    val isAdmin: Boolean
        get() = roles.contains("ADMIN")

}
