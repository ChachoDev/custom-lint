package com.example.customlintexample.domain.usecases

import com.example.customlintexample.domain.models.UserData

interface GetUserUseCase {
    suspend operator fun invoke(): UserData
}