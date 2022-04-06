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
    val user:LiveData<HttpResponse> get()=responseClassBuffer


    suspend fun getTheResponse(putUrl: String, requestMethod: String) {
        val httpHelper=HttpHelper().getTheResponse(putUrl,requestMethod)
        responseClassBuffer.postValue(httpHelper)
    }
























//
//    fun createNewContact(firstName: String, lastName: String, email: String) {
//        val params = JSONObject()
//        params.put("firstName", firstName)
//        params.put("lastName", lastName)
//        params.put("email", email)
//        val paramString = params.toString()
//        CoroutineScope(Dispatchers.IO).launch {
//            var url: URL? = null
//            try {
//                val urlConnection =
//                    HttpHelper().getUrlConnection("https://dummyapi.io/data/v1/user/create", "POST")
//                urlConnection.doInput = true
//                urlConnection.doOutput = true
//                // Send the JSON we created
//                val outputStreamWriter = OutputStreamWriter(urlConnection.outputStream)
//                outputStreamWriter.write(paramString)
//                outputStreamWriter.flush()
//        //        Log.d("post", urlConnection.responseCode.toString())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

}
