package com.example.apiused.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.adapter.DataAdapter
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ClickListener {
   private var TAG="MainActivity"

    var responseCode = 0
    private lateinit var dataViewModel: DataViewModel
    lateinit var dataAdapter: DataAdapter
    private var datalist = mutableListOf<ResponseClass>()
    private val httpHelper = HttpHelper()

    var payLoad = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = DataRepository(httpHelper)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]



        dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user", "GET", payLoad.toString())
        dataViewModel.user.observe(this) {

            buildResponseData(it)
        }

        addData.setOnClickListener {
            startActivity(Intent(this, AddNewContact::class.java))
        }

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                dataAdapter.filter(query);
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//            }
//
//        })


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
        dataAdapter = DataAdapter(datalist, this)
        recyclerView.adapter = dataAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun userClickListener(responseClass: ResponseClass) {
        val intent = Intent(this, UserDetails::class.java)
        intent.putExtra("response", responseClass)
        startActivity(intent)
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        return false
//    }
//
//    override fun onQueryTextChange(newText: String): Boolean {
//        dataAdapter.(newText)
//        return false
//    }


}