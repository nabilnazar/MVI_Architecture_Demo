package com.meghamlabs.mvi_architecture_demo.data.api

import com.meghamlabs.mvi_architecture_demo.data.model.User

interface ApiHelper {

    suspend fun getUsers(): List<User>
}