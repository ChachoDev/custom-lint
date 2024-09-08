package com.example.customlintexample.data.repositories.impl

import com.example.customlintexample.data.models.UserDto
import com.example.customlintexample.domain.models.UserData
import com.example.customlintexample.domain.repositories.UserRepository

class UseRepositoryImpl : UserRepository {

    override suspend fun getUser(): UserData {
        return userDto.toDomain()
    }

    val userDto = UserDto(
        name = "Pollo Campero",
        age = 2,
        nick = "The Rustic Chicken"

    )

    fun UserDto.toDomain() = UserData(
        name = this.name,
        age = this.age,
        nick = this.nick
    )
}