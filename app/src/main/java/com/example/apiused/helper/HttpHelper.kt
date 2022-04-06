package com.example.apiused.helper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpHelper {

    private val TAG = "HttpHelper"

    var responseCode: Int = 0
    suspend fun getTheResponse(putUrl: String, requestMethod: String): HttpResponse {
        Log.d(TAG, "StartingOfTheMethod")

        val stringBuffer = StringBuffer()

        try {
            var url: URL? = null
            url = URL(putUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = requestMethod
            urlConnection.setRequestProperty("Content-Type", "application/json")
            urlConnection.setRequestProperty("app-id", "6246a11467f32b430c69b150")

            // urlConnection.doInput = requestMethod != "GET"

            if (requestMethod == "PUT" || requestMethod == "POST") {
                urlConnection.doInput = true
                urlConnection.doOutput = true
            }


            val inputStream = urlConnection.inputStream

            // read the stream of data received from the server
            val inputStreamReader = InputStreamReader(inputStream)

            //inputStreamReader.read will give one element at a time
            // so data will have the first element
            var data = inputStreamReader.read()

            // StringBuffer class is used to build the json response int the string format
            // when data is -1 we reached the end of the response
            while (data != -1) {
                // the data will be in byte format so we are cast it to char
                val ch = data.toChar()
                stringBuffer.append(ch)
                data = inputStreamReader.read()
            }
            //  responseClassBuffer.postValue(stringBuffer)
            responseCode = urlConnection.responseCode
            Log.d(TAG, "responseCode" + urlConnection.responseCode.toString())
            Log.d("tag", stringBuffer.toString())
            return HttpResponse(responseCode, stringBuffer.toString())
        } catch (e: Exception) {

            e.printStackTrace()
        }
        Log.d(TAG, "outSideTheScope")
        return HttpResponse(responseCode, stringBuffer.toString())
    }

}