package com.example.pogodynka.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pogodynka.R
import com.example.pogodynka.model.Weather
import com.example.pogodynka.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<Weather>> = MutableLiveData()
    var seniorMode = false

    fun getWeather(city: String){
        viewModelScope.launch {
            val response = repository.getWeather(city, "8847a9b1bbc58f34f4fb35d778dcc46e")
            myResponse.value = response
        }
    }
}