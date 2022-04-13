package com.example.apiused.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.helper.HttpHelper
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.item_layout.*
import org.json.JSONObject

class UpdateTheContact : AppCompatActivity() {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var uri: Uri
    var payLoad=JSONObject()
    private val httpHelper=HttpHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val repository = DataRepository(httpHelper)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]

        // back to contact details
        ivCross.setOnClickListener{
            onBackPressed()
        }
        val intent = intent
        val responseClass = intent.getParcelableExtra<ResponseClass>("response")
        if (responseClass != null) {
            Glide.with(this)
                .load(responseClass.picture)
                .into(etImage as ImageView)
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

        var contactId=intent.getStringExtra("id")

        btnUpdate.setOnClickListener {
            payLoad.put("title",etTittle.text.toString())
            payLoad.put("firstName",et_firstName.text.toString())
            payLoad.put("lastName",et_lastName.text.toString())
            payLoad.put("gender",et_Gender.text.toString())
            payLoad.put("email",et_email.text.toString())
            payLoad.put("phone",et_phoneNo.text.toString())
            payLoad.put("street",etStreet.text.toString())
            payLoad.put("state",etState.text.toString())
            payLoad.put("city",etCity.text.toString())
            payLoad.put("country",etCountry.text.toString())
            payLoad.put("timezone",etTimezone.text.toString())
            if (responseClass != null) {
                dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/${contactId}",
                    "PUT",
                    payLoad.toString())
            }
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
           startActivity(Intent(this,MainActivity::class.java))
        }

        etImage.setOnClickListener{
            selectImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==10){
            uri= data?.data!!
            Glide.with(etImage).load(uri).into(etImage as ImageView)
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