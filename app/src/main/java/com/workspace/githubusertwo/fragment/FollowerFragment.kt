package com.workspace.githubusertwo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.workspace.githubusertwo.R
import com.workspace.githubusertwo.adapter.UserAdapter
import com.workspace.githubusertwo.api.ApiClient
import com.workspace.githubusertwo.model.UserModel
import kotlinx.android.synthetic.main.fragment_follower.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private var getUserID : String = "username"

    companion object {
        @JvmStatic
        private  val USERNAME = "username"
        fun newInstance(username: String): FollowerFragment {
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.fragment_follower, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerUser()
        arguments.let {
            getUserID = it?.getString(USERNAME).toString()

        }
        getFollowers(getUserID)


    }

    private fun recyclerUser() {
        adapter = UserAdapter()
        rvFollower.layoutManager = LinearLayoutManager(context)
        rvFollower.setHasFixedSize(true)
        rvFollower.adapter = adapter
    }

    private fun getFollowers(userName: String) {
        val call = ApiClient.service.getFollowers(userName)
        call.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>, response: Response<List<UserModel>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body().orEmpty()
                    adapter.addAll(list)
                }
            }
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {

            }

        })
    }

}


