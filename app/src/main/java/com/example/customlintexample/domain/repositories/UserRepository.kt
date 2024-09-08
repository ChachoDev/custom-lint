package com.example.customlintexample.domain.repositories

import com.example.customlintexample.domain.models.UserData

interface UserRepository {
    suspend fun getUser() : UserData
}