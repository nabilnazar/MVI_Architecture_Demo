package com.meghamlabs.mvi_architecture_demo.data.api

import com.meghamlabs.mvi_architecture_demo.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}