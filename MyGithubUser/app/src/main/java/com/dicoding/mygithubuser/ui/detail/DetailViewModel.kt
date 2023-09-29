package com.dicoding.mygithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubuser.data.response.DetailResponse
import com.dicoding.mygithubuser.data.response.ItemsItem
import com.dicoding.mygithubuser.data.retrofit.ApiConfig
import com.dicoding.mygithubuser.ui.follow.FollowFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _userData = MutableLiveData<DetailResponse>()
    val userData: LiveData<DetailResponse> = _userData

    private val _listFollowers = MutableLiveData<ArrayList<ItemsItem>>()
    val listFollowers: LiveData<ArrayList<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<ArrayList<ItemsItem>>()
    val listFollowing: LiveData<ArrayList<ItemsItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    if(response.body() != null) {
                        _userData.postValue(response.body())
                    }
                }else {
                    Log.e(DetailActivity.TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailActivity.TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun getListFollowers(user: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(call: Call<ArrayList<ItemsItem>>, response: Response<ArrayList<ItemsItem>>) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    if(response.body() != null) {
                        _listFollowers.value = response.body()
                    }
                }else {
                    Log.e(FollowFragment.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowFragment.TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun getListFollwing(user: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(call: Call<ArrayList<ItemsItem>>, response: Response<ArrayList<ItemsItem>>) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    if(response.body() != null) {
                        _listFollowing.value = response.body()
                    }
                }else {
                    Log.e(FollowFragment.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowFragment.TAG, "onFailure : ${t.message}")
            }
        })
    }
}