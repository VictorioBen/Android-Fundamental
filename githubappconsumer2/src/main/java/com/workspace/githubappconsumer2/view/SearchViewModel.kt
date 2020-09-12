package com.workspace.githubappconsumer2.view


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.workspace.githubappconsumer2.api.ApiClient
import com.workspace.githubappconsumer2.model.SearchResponse
import com.workspace.githubappconsumer2.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val list: MutableLiveData<List<UserModel>> = MutableLiveData()
    val listResult: LiveData<List<UserModel>> = list


    fun onResponse(query: String) {
        val call = ApiClient.service.getSearchResult(query)
        call.enqueue(object : Callback<SearchResponse> {

            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
                list.postValue(emptyList())

            }

            override fun onResponse(
                call: Call<SearchResponse>?, response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val data: List<UserModel> = response.body()?.items!!
                    list.postValue(data)
                        if (data.isEmpty()){
                            list.postValue(data)
                        }
                }

            }

        })
    }

}


