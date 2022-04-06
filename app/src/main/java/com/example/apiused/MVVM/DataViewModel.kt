package com.example.apiused.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val user: LiveData<HttpResponse> get() = dataRepository.user

    fun getTheResponse(putUrl: String, requestMethod: String) {
        viewModelScope.launch (Dispatchers.IO){
         dataRepository.getTheResponse(putUrl, requestMethod)
        }

    }

}