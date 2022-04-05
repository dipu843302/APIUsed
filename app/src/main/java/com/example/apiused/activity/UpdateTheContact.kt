package com.example.apiused.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.example.apiused.R
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.item_layout.*

class UpdateTheContact : AppCompatActivity() {
    private lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // back to contact details
        ivCross.setOnClickListener{
            onBackPressed()
        }
        val intent = intent
        val responseClass = intent.getParcelableExtra<ResponseClass>("response")
        if (responseClass != null) {
            Glide.with(this)
                .load(responseClass.picture)
                .into(etImage)
        }

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

        etImage.setOnClickListener{
            selectImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==10){
            uri= data?.data!!
            Glide.with(etImage).load(uri).into(etImage)
          //  Log.d("imageUri",uri.toString())

        }
    }
    private fun selectImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        startActivityForResult(chooserIntent, 10)
    }

}