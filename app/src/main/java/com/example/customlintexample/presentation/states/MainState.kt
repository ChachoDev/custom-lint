package com.example.customlintexample.presentation.states

import com.example.customlintexample.domain.models.UserData

sealed class MainState {
    object Loading : MainState()
    data class Characters(val userData: UserData) : MainState()
}
