package com.example.customlintexample.domain.usecases.impl

import com.example.customlintexample.domain.models.UserData
import com.example.customlintexample.domain.repositories.UserRepository
import com.example.customlintexample.domain.usecases.GetUserUseCase

class GetUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserUseCase {
    override suspend fun invoke(): UserData {
        return userRepository.getUser()
    }
}