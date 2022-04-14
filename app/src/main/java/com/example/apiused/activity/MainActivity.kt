package com.example.apiused.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ClickListener {
    var TAG="MainActivity"
    private lateinit var dataViewModel: DataViewModel
    lateinit var dataAdapter: DataAdapter
    private val httpHelper = HttpHelper()
    var payLoad = JSONObject()

    private var datalist = mutableListOf<ResponseClass>()
    var displayList = ArrayList<ResponseClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

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
            displayList.addAll(datalist)
            setRecyclerView()

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerView() {
        dataAdapter = DataAdapter(displayList, this)
        recyclerView.adapter = dataAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun userClickListener(responseClass: ResponseClass) {
        val intent = Intent(this, UserDetails::class.java)
        intent.putExtra("response", responseClass)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()

                        val search = newText.lowercase(Locale.getDefault())
                        datalist.forEach {
                            if (it.toString().lowercase(Locale.getDefault()).contains(search)) {
                                displayList.add(it)
                                Log.d(TAG, "data $it")
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }else{
                        displayList.clear()
                        displayList.addAll(datalist)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}

