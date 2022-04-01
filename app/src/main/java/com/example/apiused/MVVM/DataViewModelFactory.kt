package com.example.apiused.MVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DataViewModelFactory(val dataRepository: DataRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(dataRepository) as T
    }
}