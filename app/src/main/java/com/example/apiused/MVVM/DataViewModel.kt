package com.example.apiused.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(private val dataRepository: DataRepository):ViewModel() {

    val user:LiveData<StringBuffer> get()=dataRepository.userBuffer

    fun fetchTheContact(){
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.fetchTheContact()
        }
    }
    fun fetchContactDetailsById( id:String){
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.fetchContactDetailsById(id)
        }
    }

    fun createNewContact(firstName: String, lastName: String,email:String) {
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.createNewContact(firstName,lastName,email)
        }
    }

    fun deleteTheContact(id: String) {
        viewModelScope.launch (Dispatchers.IO){
            dataRepository.deleteTheContact(id)
        }
    }
}