package com.example.apiused.MVVM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apiused.helper.HttpHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class DataRepository {
    private val responseClassBuffer = MutableLiveData<StringBuffer>()
    val userBuffer: LiveData<StringBuffer> get() = responseClassBuffer

    fun fetchTheContact() {
        val stringBuffer = StringBuffer()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val urlConnection =
                    HttpHelper().getUrlConnection("https://dummyapi.io/data/v1/user", "GET")

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
                responseClassBuffer.postValue(stringBuffer)
              //  Log.d("get", urlConnection.responseCode.toString())
             //   Log.d("gett", stringBuffer.toString())

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun fetchContactDetailsById(id: String) {
        val stringBuffer = StringBuffer()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val urlConnection =
                    HttpHelper().getUrlConnection("https://dummyapi.io/data/v1/user/$id", "GET")

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
              //  responseClassBufferId.postValue(stringBuffer)
                responseClassBuffer.postValue(stringBuffer)
            //    Log.d("getById", urlConnection.responseCode.toString())
              //  Log.d("gett", stringBuffer.toString())

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun createNewContact(firstName: String, lastName: String, email: String) {
        val params = JSONObject()
        params.put("firstName", firstName)
        params.put("lastName", lastName)
        params.put("email", email)
        val paramString = params.toString()
        CoroutineScope(Dispatchers.IO).launch {
            var url: URL? = null
            try {
                val urlConnection =
                    HttpHelper().getUrlConnection("https://dummyapi.io/data/v1/user/create", "POST")
                urlConnection.doInput = true
                urlConnection.doOutput = true
                // Send the JSON we created
                val outputStreamWriter = OutputStreamWriter(urlConnection.outputStream)
                outputStreamWriter.write(paramString)
                outputStreamWriter.flush()
        //        Log.d("post", urlConnection.responseCode.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTheContact(id: String) {
        val httpURLConnection =
            HttpHelper().getUrlConnection("https://dummyapi.io/data/v1/user/$id", "DELETE")
        httpURLConnection.doOutput = true
        httpURLConnection.setRequestProperty("Content-Type", "application/json")
        httpURLConnection.setRequestProperty("app-id", "6246a11467f32b430c69b150")
        httpURLConnection.connect()
     //   Log.d("delete", httpURLConnection.responseCode.toString())
    }
}
