package com.example.apiused.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.helper.HttpHelper
import kotlinx.android.synthetic.main.activity_add.*
import org.json.JSONObject

class AddNewContact : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel
    var payLoad = JSONObject()
    private val httpHelper=HttpHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val repository = DataRepository(httpHelper)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]

        btnCreate.setOnClickListener {
            payLoad.put("firstName",etfirstName.text.toString())
            payLoad.put("lastName",etlastName.text.toString())
            payLoad.put("email",etEmail.text.toString())
            dataViewModel.getTheResponse(
                "https://dummyapi.io/data/v1/user/create",
                "POST",
                payLoad.toString()
            )
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        ivBack.setOnClickListener{
            onBackPressed()
        }
    }
}