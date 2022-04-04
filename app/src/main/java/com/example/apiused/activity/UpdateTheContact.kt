package com.example.apiused.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apiused.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_user_details.*

class UpdateTheContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // back to contact details
        ivCross.setOnClickListener{
            onBackPressed()
        }
        val intent = intent
        etTittle.setText(intent.getStringExtra("tittle"))
        et_firstName.setText(intent.getStringExtra("firstName"))
        et_lastName.setText(intent.getStringExtra("lastName"))
        et_Gender.setText(intent.getStringExtra("gender"))
        et_email.setText(intent.getStringExtra("email"))
        et_phoneNo.setText(intent.getStringExtra("phone"))
        etStreet.setText(intent.getStringExtra("street"))
        etState.setText(intent.getStringExtra("state"))
        etCity.setText(intent.getStringExtra("city"))
        etCountry.setText(intent.getStringExtra("country"))
        etTimezone.setText(intent.getStringExtra("timezone"))
    }
}