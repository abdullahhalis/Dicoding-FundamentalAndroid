package com.dicoding.mygithubuser.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubuser.data.FavoriteUserRepository
import com.dicoding.mygithubuser.data.remote.response.GithubResponse
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        getUsers(QUERY)
    }

    fun getUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun getThemeSetting():LiveData<Boolean> {
        return favoriteUserRepository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            favoriteUserRepository.saveThemeSetting(isDarkModeActive)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val QUERY = "abdullah"
    }
}