package com.example.apiused.MVVM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL


class DataRepository {
    private val responseClassBuffer = MutableLiveData<HttpResponse>()
    val user: LiveData<HttpResponse> get() = responseClassBuffer


     fun getTheResponse(putUrl: String, requestMethod: String,arrayList: ArrayList<String>) {
         CoroutineScope(Dispatchers.IO).launch {
             val httpHelper = HttpHelper().getTheResponse(putUrl, requestMethod, arrayList)
             responseClassBuffer.postValue(httpHelper)
         }

    }

}
