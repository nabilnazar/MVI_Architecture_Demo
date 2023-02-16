package com.meghamlabs.mvi_architecture_demo.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meghamlabs.mvi_architecture_demo.data.api.ApiHelper
import com.meghamlabs.mvi_architecture_demo.data.repository.MainRepository
import com.meghamlabs.mvi_architecture_demo.ui.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}