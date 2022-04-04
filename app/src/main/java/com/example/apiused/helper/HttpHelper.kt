package com.example.apiused.helper

import java.net.HttpURLConnection
import java.net.URL

class HttpHelper {

    fun getUrlConnection(putUrl: String, requestMethod: String): HttpURLConnection {
        var url: URL? = null
        url = URL(putUrl)
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = requestMethod
        urlConnection.setRequestProperty("Content-Type","application/json")
        urlConnection.setRequestProperty("app-id","6246a11467f32b430c69b150")
        return urlConnection
    }

}