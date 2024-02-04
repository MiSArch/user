package org.misarch.user.event

import io.dapr.Topic
import io.dapr.client.domain.CloudEvent
import org.misarch.user.event.model.CreateUserDTO
import org.misarch.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Controller for Dapr events
 *
 * @param userService the service for users
 */
@Controller
class DaprEventController(
    private val userService: UserService
) {

    /**
     * Handles user creation events
     *
     * @param cloudEvent the event to handle
     */
    @Topic(name = UserEvents.USER_CREATE, pubsubName = UserEvents.PUBSUB_NAME)
    @PostMapping("/subscription/${UserEvents.USER_CREATE}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun onUserCreate(
        @RequestBody
        cloudEvent: CloudEvent<CreateUserDTO>
    ) {
        userService.createUser(cloudEvent.data)
    }

}
