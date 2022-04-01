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

class Add : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val repository = DataRepository()
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]

        btnAdd.setOnClickListener{
            dataViewModel.postData(etFirstName.text.toString(),etLastName.text.toString(),etEmail.text.toString())
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}