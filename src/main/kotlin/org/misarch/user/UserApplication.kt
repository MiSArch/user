package org.misarch.user

import com.infobip.spring.data.r2dbc.EnableQuerydslR2dbcRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableQuerydslR2dbcRepositories
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}