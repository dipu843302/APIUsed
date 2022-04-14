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
import androidx.recyclerview.widget.RecyclerView
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.adapter.DataAdapter
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ClickListener {
    var search=""
    lateinit var linearLayoutManager: LinearLayoutManager
    var position = 0
    var lastPosition = 0
    var TAG = "MainActivity"
    private lateinit var dataViewModel: DataViewModel
    lateinit var dataAdapter: DataAdapter
    private val httpHelper = HttpHelper()
    var payLoad = JSONObject()

    private var datalist = mutableListOf<ResponseClass>()
    var displayList = ArrayList<ResponseClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = DataRepository(httpHelper)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]

        apiCall()

        dataViewModel.user.observe(this) {

            buildResponseData(it)
            Log.d("dipu", "fetch data ${it.response[0]}")
        }
        addData.setOnClickListener {
            startActivity(Intent(this, AddNewContact::class.java))
        }

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.itemCount - 1 && lastPosition != datalist.size && search.isEmpty()) {
                    position++
                    apiCall()
                    Log.d("position", "$position $lastPosition")
                    lastPosition = datalist.size
                }

                super.onScrolled(recyclerView, dx, dy)

            }

        })
    }

    private fun apiCall() {
        dataViewModel.getTheResponse(
            "https://dummyapi.io/data/v1/user?limit=50&page=$position",
            "GET",
            payLoad.toString()
        )
    }

    private fun buildResponseData(httpResponse: HttpResponse) {
        val json = httpResponse.response
        try {
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("data")
            var id: String? = null
            var title: String? = null
            var firstName: String? = null
            var lastName: String? = null
            var picture: String? = null

            for (i in 0 until jsonArray.length()) {
                try {
                    val eachJsonObjects = jsonArray.getJSONObject(i)
                    id = eachJsonObjects.getString("id")
                    firstName = eachJsonObjects.getString("firstName")
                    lastName = eachJsonObjects.getString("lastName")
                    title = eachJsonObjects.getString("title")
                    picture = eachJsonObjects.getString("picture")
                } catch (e: Exception) {
                    print(e)
                } finally {
                    val responseClass = ResponseClass(id, title, firstName, lastName, picture)
                    datalist.add(responseClass)
                }
                setRecyclerView()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setRecyclerView() {
        dataAdapter = DataAdapter(datalist, this)
        recyclerView.adapter = dataAdapter
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun userClickListener(responseClass: ResponseClass) {
        val intent = Intent(this, UserDetails::class.java)
        intent.putExtra("response", responseClass)
        startActivity(intent)
    }


    // for searching
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

                         search = newText.lowercase(Locale.getDefault())
                        datalist.forEach {
                            if (it.firstName?.lowercase(Locale.getDefault())!!?.contains(search)) {
                                displayList.add(it)
                                Log.d(TAG, "data $it")
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    } else {
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

