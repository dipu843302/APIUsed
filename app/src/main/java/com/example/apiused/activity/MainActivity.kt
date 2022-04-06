package com.example.apiused.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.adapter.DataAdapter
import com.example.apiused.helper.HttpResponse
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(),ClickListener {
//   private var TAG="MainActivity"

    var responseCode=0
    private lateinit var dataViewModel: DataViewModel
    lateinit var dataAdapter: DataAdapter
    private var datalist = mutableListOf<ResponseClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = DataRepository()
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]



            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user","GET")
            dataViewModel.user.observe(this) {
                   Log.d("dipu", it.toString())
                buildResponseData(it)
            }





        addData.setOnClickListener{
            startActivity(Intent(this,AddNewContact::class.java))
        }
    }

    private fun buildResponseData(httpResponse: HttpResponse) {
       val json = httpResponse.response
        try {
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val eachJsonObjects = jsonArray.getJSONObject(i)
                val id = eachJsonObjects.getString("id")
                val title = eachJsonObjects.getString("title")
                val firstName = eachJsonObjects.getString("firstName")
                val lastName = eachJsonObjects.getString("lastName")
                val picture = eachJsonObjects.getString("picture")

                val responseClass = ResponseClass(id, title, firstName, lastName, picture)
                datalist.add(responseClass)
            }
            setRecyclerView()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerView() {
        dataAdapter = DataAdapter(datalist,this)
        recyclerView.adapter = dataAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun userClickListener(responseClass: ResponseClass) {
        val intent=Intent(this,UserDetails::class.java)
        intent.putExtra("response",responseClass)
        startActivity(intent)
    }


}