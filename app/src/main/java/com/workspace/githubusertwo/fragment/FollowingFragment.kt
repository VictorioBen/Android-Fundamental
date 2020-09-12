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
import kotlinx.android.synthetic.main.fragment_following.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private var getUserID: String = ""

    companion object {
        private const val USERNAME = "username"
        fun newInstance(username: String): FollowingFragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerUser()
        arguments.let {
            getUserID = it?.getString(USERNAME).toString()
            getFollowing(getUserID)
        }
    }

    private fun recyclerUser() {
        adapter = UserAdapter()
        rvFollowing.layoutManager = LinearLayoutManager(context)
        rvFollowing.setHasFixedSize(true)
        rvFollowing.adapter = adapter
    }

    private fun getFollowing(userName: String) {
        val call = ApiClient.service.getFollowing(userName)
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