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
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_update.*
import org.json.JSONObject

class AddNewContact : AppCompatActivity() {
    private lateinit var uri: Uri
    private lateinit var dataViewModel: DataViewModel
    var payLoad = JSONObject()
    private val httpHelper = HttpHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val repository = DataRepository(httpHelper)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]

        btnCreate.setOnClickListener {
            payLoad.put("firstName", etfirstName.text.toString())
            payLoad.put("lastName", etlastName.text.toString())
            payLoad.put("email", etEmail.text.toString())

            dataViewModel.getTheResponse(
                "https://dummyapi.io/data/v1/user/create",
                "POST",
                payLoad.toString()
            )
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==20){
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
        startActivityForResult(chooserIntent, 20)
    }
}