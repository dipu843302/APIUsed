package com.example.apiused.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.item_layout.*
import kotlinx.android.synthetic.main.item_layout.image

class UserDetails : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val repository = DataRepository()
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]


        val intent = intent
        val responseClass = intent.getParcelableExtra<ResponseClass>("response")
        if (responseClass != null) {
            Glide.with(this)
                .load(responseClass.picture)
                .into(image)

            tvName.text = responseClass.firstName
        }

        // back to MainActivity
        back.setOnClickListener {
            onBackPressed()
        }

        //update the contact
        btnEdit.setOnClickListener {

        }

        // delete the contact
        btnDelete.setOnClickListener {
            if (responseClass != null) {
                dataViewModel.deleteTheContact(responseClass.id)
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}