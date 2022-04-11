package com.example.apiused.viewModelTest

import com.example.apiused.MVVM.DataRepository
import com.example.apiused.MVVM.DataViewModel
import com.example.apiused.helper.HttpResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ViewModelTest {

    private lateinit var dataViewModel: DataViewModel

    @MockK
    private lateinit var dataRepository: DataRepository
    @MockK
    var arraylist=ArrayList<String>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataViewModel = DataViewModel(dataRepository)
    }

    @Test
    fun fetchTheContact(){
        coEvery {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user","GET", arraylist)
        } returns Unit
        runBlocking {
            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user","GET", arraylist)
        }
        coVerify {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user","GET", arraylist)
        }
    }
    @Test
    fun fetchTheContactById(){
        coEvery {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95","GET", arraylist)
        } returns Unit
        runBlocking {
            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95","GET", arraylist)
        }
        coVerify {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/6246f777cb2f3f6743a05b95","GET", arraylist)
        }
    }
    @Test
    fun deleteTheContactById(){
        coEvery {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109ed","DELETE", arraylist)
        } returns Unit
        runBlocking {
            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109ed","DELETE", arraylist)
        }
        coVerify {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109ed","DELETE", arraylist)
        }
    }

    @Test
    fun createNewContact(){
        coEvery {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/create","POST", arraylist)
        } returns Unit
        runBlocking {
            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/create","POST", arraylist)
        }
        coVerify {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/create","POST", arraylist)
        }
    }

    @Test
    fun updateTheContact(){
        coEvery {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109e1","PUT", arraylist)
        } returns Unit
        runBlocking {
            dataViewModel.getTheResponse("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109e1","PUT", arraylist)
        }
        coVerify {
            dataRepository.getTheMyResponse("https://dummyapi.io/data/v1/user/60d0fe4f5311236168a109e1","PUT", arraylist)
        }
    }
}