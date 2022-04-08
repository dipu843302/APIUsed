package com.example.apiused.repositoryTest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import com.example.apiused.models.ResponseClass
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    private val httpResponseLiveData=MutableLiveData<HttpResponse>()
    private lateinit var contactData:LiveData<HttpResponse>

    private lateinit var dataRepository: DataRepository
    @MockK
    var arraylist=ArrayList<String>()

    @MockK
    lateinit var httpHelper: HttpHelper

    @MockK
    lateinit var httpResponse: HttpResponse

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        contactData=httpResponseLiveData
        dataRepository= DataRepository()
    }

    @Test
    fun fetchTheContact_Testing(){
        coEvery {
            httpHelper.getTheResponse("https://dummyapi.io/data/v1/user","GET", arraylist)
        } returns httpResponse
        runBlocking {
            dataRepository.getTheResponse("https://dummyapi.io/data/v1/user","GET", arraylist)
        }
        coVerify {
            httpHelper.getTheResponse("https://dummyapi.io/data/v1/user","GET", arraylist)
        }
    }
}