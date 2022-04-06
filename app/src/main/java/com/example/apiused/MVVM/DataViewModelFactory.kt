package com.example.apiused.MVVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DataViewModelFactory(val dataRepository: DataRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(dataRepository) as T
    }
}

//fun getTheResponse(putUrl: String, requestMethod: String): Int {
//
//    val stringBuffer = StringBuffer()
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            var url: URL? = null
//            url = URL(putUrl)
//            val urlConnection = url.openConnection() as HttpURLConnection
//            urlConnection.requestMethod = requestMethod
//            urlConnection.setRequestProperty("Content-Type", "application/json")
//            urlConnection.setRequestProperty("app-id", "6246a11467f32b430c69b150")
//
//            val inputStream = urlConnection.inputStream
//
//            // read the stream of data received from the server
//            val inputStreamReader = InputStreamReader(inputStream)
//
//            //inputStreamReader.read will give one element at a time
//            // so data will have the first element
//            var data = inputStreamReader.read()
//
//            // StringBuffer class is used to build the json response int the string format
//            // when data is -1 we reached the end of the response
//            while (data != -1) {
//                // the data will be in byte format so we are cast it to char
//                val ch = data.toChar()
//                stringBuffer.append(ch)
//                data = inputStreamReader.read()
//            }
//            responseClassBuffer.postValue(stringBuffer)
//            responseCode=urlConnection.responseCode
//            Log.d("get", urlConnection.responseCode.toString())
//            //   Log.d("gett", stringBuffer.toString())
//
//        } catch (e: Exception) {
//
//            e.printStackTrace()
//        }
//    }
//    return responseCode
//}