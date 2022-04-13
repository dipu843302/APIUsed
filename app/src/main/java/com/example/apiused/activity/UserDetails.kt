package com.example.apiused.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.MVVM.DataViewModelFactory
import com.example.apiused.R
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.item_layout.image
import org.json.JSONException
import org.json.JSONObject

class UserDetails : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel
   var payLaod=JSONObject()
    private val httpHelper=HttpHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val repository = DataRepository(httpHelper)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProviders.of(this, viewModelFactory)[DataViewModel::class.java]


        val intent = intent
        val responseClass = intent.getParcelableExtra<ResponseClass>("response")
        if (responseClass != null) {
            Glide.with(this)
                .load(responseClass.picture)
                .into(image as ImageView)

            tvName.text = responseClass.firstName
        }

        val id = responseClass?.id
        if (id != null) {
            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/$id","GET",payLaod.toString())
            dataViewModel.user.observe(this) {
                buildResponseData(it)
            }

        }

        // back to MainActivity
        back.setOnClickListener {
            onBackPressed()
        }

        //update the contact
        btnEdit.setOnClickListener {
           val intent=Intent(this,UpdateTheContact::class.java)
            intent.putExtra("id",id)
            intent.putExtra("response",responseClass)
            intent.putExtra("tittle",tvTittle.text.toString())
            intent.putExtra("firstName",tvFirstName.text.toString())
            intent.putExtra("lastName",tvLastName.text.toString())
            intent.putExtra("gender",tvGender.text.toString())
            intent.putExtra("email",tvEmail.text.toString())
            intent.putExtra("phone",tvPhone.text.toString())
            intent.putExtra("street",tVStreet.text.toString())
            intent.putExtra("city",tvCity.text.toString())
            intent.putExtra("state",tvState.text.toString())
            intent.putExtra("country",tvCountry.text.toString())
            intent.putExtra("timezone",tvTimezone.text.toString())
            startActivity(intent)
        }

        // delete the contact
        btnDelete.setOnClickListener {
            if (responseClass != null) {
                dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/$id","DELETE",payLaod.toString())
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buildResponseData(httpResponse: HttpResponse) {

        val json = httpResponse.response
        try {
            val jsonObject = JSONObject(json)

            val eachJsonObjects = jsonObject.getJSONObject("location")

            val id = jsonObject.getString("id")
            val title = jsonObject.getString("title")
            val firstName = jsonObject.getString("firstName")
            val lastName = jsonObject.getString("lastName")
            val picture = jsonObject.getString("picture")
            val gender = jsonObject.getString("gender")
            val email = jsonObject.getString("email")
            val dateOfBirth = jsonObject.getString("dateOfBirth")
            val phone = jsonObject.getString("phone")

            val street = eachJsonObjects.getString("street")
            val city = eachJsonObjects.getString("city")
            val state = eachJsonObjects.getString("state")
            val country = eachJsonObjects.getString("country")
            val timezone = eachJsonObjects.getString("timezone")


            tvTittle.text = title
            tvFirstName.text = firstName
            tvLastName.text = lastName
            tvEmail.text = email
            tvDOB.text = dateOfBirth
            tvPhone.text = phone
            tvGender.text=gender
            tVStreet.text = street
            tvCity.text = city
            tvState.text = state
            tvCountry.text = country
            tvTimezone.text = timezone

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}