package com.meghamlabs.mvi_architecture_demo.ui.viewstate

import com.meghamlabs.mvi_architecture_demo.data.model.User

sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class Users(val user: List<User>) : MainState()
    data class Error(val error: String?) : MainState()

}