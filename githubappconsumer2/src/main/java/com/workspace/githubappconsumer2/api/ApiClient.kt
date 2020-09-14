package com.workspace.githubappconsumer2.api

import com.workspace.githubappconsumer2.model.SearchResponse
import com.workspace.githubappconsumer2.model.UserModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

class ApiClient {
    companion object {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/").build()
        }

        val service by lazy {
            val create: ApiService = retrofit.create(ApiService::class.java)
            create
        }
    }
}

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 61edfbfd146515eca5e8724b548351cfe3184ba1")
    fun getSearchResult(
        @Query("q") q: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<UserModel>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserModel>>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserModel>>
}