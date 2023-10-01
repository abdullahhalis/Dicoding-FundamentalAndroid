package com.dicoding.mygithubuser.data.remote.retrofit

import com.dicoding.mygithubuser.data.remote.response.DetailResponse
import com.dicoding.mygithubuser.data.remote.response.GithubResponse
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun getListUsers(
        @Query("q") q: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String?
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String?
    ): Call<ArrayList<ItemsItem>>
}
