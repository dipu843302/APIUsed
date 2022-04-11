package com.example.apiused.repositoryTest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.apiused.MVVM.DataRepository
import com.example.apiused.helper.HttpHelper
import com.example.apiused.helper.HttpResponse
import com.example.apiused.models.ResponseClass
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
class RepositoryTest {

    private val httpResponseLiveData = MutableLiveData<HttpResponse>()
    private lateinit var contactData: LiveData<HttpResponse>

    private lateinit var dataRepository: DataRepository

    var arraylist = arrayListOf<String>()

    lateinit var httpHelper: HttpHelper

    lateinit var httpResponse: HttpResponse


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        httpHelper = mockk()
        contactData = httpResponseLiveData
        dataRepository = DataRepository(httpHelper)

    }

    @Test
    fun fetchTheContact() {
        httpResponse = HttpResponse(300, "true")
        coEvery {
            httpHelper.getTheResponse("https://dummyapi.io/data/v1/user", "GET", arraylist)
        } returns httpResponse
        runBlocking {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user", "GET", arraylist)
        }
        coVerify {
            httpHelper.getTheResponse("https://dummyapi.io/data/v1/user", "GET", arraylist)
        }
    }

    @Test
    fun fetchTheContactById() {
        httpResponse = HttpResponse(200, "true")
        coEvery {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95",
                "GET",
                arraylist
            )
        } returns httpResponse
        runBlocking {
            dataRepository.getTheMyResponse(
                "https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95",
                "GET",
                arraylist
            )
        }
        coVerify {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95",
                "GET",
                arraylist
            )
        }
    }

    @Test
    fun deleteTheContactById() {
        httpResponse = HttpResponse(200, "true")
        coEvery {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95",
                "DELETE",
                arraylist
            )
        } returns httpResponse
        runBlocking {
            dataRepository.getTheMyResponse(
                "https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95",
                "DELETE",
                arraylist
            )
        }
        coVerify {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95",
                "DELETE",
                arraylist
            )
        }
    }

    @Test
    fun CreateTheContact(){
        httpResponse = HttpResponse(200, "true")
        coEvery {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/create",
                "POST",
                arraylist
            )
        } returns httpResponse
        runBlocking {
            dataRepository.getTheMyResponse(
                "https://dummyapi.io/data/v1/user/create",
                "POST",
                arraylist
            )
        }
        coVerify {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/create",
                "POST",
                arraylist
            )
        }
    }
    @Test
    fun updateTheContact(){
        httpResponse = HttpResponse(200, "true")
        coEvery {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109e1",
                "PUT",
                arraylist
            )
        } returns httpResponse
        runBlocking {
            dataRepository.getTheMyResponse(
                "https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109e1",
                "PUT",
                arraylist
            )
        }
        coVerify {
            httpHelper.getTheResponse(
                "https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109e1",
                "PUT",
                arraylist
            )
        }
    }
}