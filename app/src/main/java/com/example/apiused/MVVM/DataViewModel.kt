package com.example.apiused.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(private val dataRepository: DataRepository):ViewModel() {

    val user:LiveData<StringBuffer> get()=dataRepository.userBuffer

    fun openTheConnection(){
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.openTheConnection()
        }
    }

    fun postData(firstName: String, lastName: String,email:String) {
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.postData(firstName,lastName,email)
        }
    }

    fun Delete(id: String) {
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.Delete(id)
        }
    }
}