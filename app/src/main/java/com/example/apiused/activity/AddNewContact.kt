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
import kotlinx.android.synthetic.main.activity_add.*

class AddNewContact : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel
    var arrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val repository = DataRepository()
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]

        btnCreate.setOnClickListener {
            arrayList.add(etfirstName.text.toString())
            arrayList.add(etlastName.text.toString())
            arrayList.add(etEmail.text.toString())
            dataViewModel.getTheResponse(
                "https://dummyapi.io/data/v1/user/create",
                "PUT",
                arrayList
            )
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        ivBack.setOnClickListener{
            onBackPressed()
        }
    }
}