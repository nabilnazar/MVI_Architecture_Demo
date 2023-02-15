package com.meghamlabs.mvi_architecture_demo.data.repository

import com.meghamlabs.mvi_architecture_demo.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}