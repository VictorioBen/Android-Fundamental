@file:Suppress("DEPRECATION")

package com.workspace.githubusertwo.activity


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.workspace.githubusertwo.R
import com.workspace.githubusertwo.adapter.UserAdapter
import com.workspace.githubusertwo.adapter.ViewPagerAdapter
import com.workspace.githubusertwo.api.ApiClient
import com.workspace.githubusertwo.db.DatabaseContract
import com.workspace.githubusertwo.helper.UserHelper
import com.workspace.githubusertwo.fragment.FollowerFragment
import com.workspace.githubusertwo.fragment.FollowingFragment
import com.workspace.githubusertwo.model.UserModel
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.layout_detail_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DetailUserActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var helper: UserHelper
    private lateinit var userAdapter: UserAdapter
    private val langKey: String = "settings"
    companion object {
        const val EXTRA_NAME = "name"
        private val TAG = SearchActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        title = getString(R.string.detail)
        helper = UserHelper.getInstance(applicationContext)
        helper.open()
        loadLocate()
        userAdapter = UserAdapter()
        val userModel = intent.getParcelableExtra<UserModel>(EXTRA_NAME)
        val userID = userModel?.userName ?: ""
        pagerAdapter = ViewPagerAdapter(supportFragmentManager, this)
        detailPager.adapter = pagerAdapter
        tabDetail.setupWithViewPager(detailPager)
        getDetailUser(userID)
        getFollowers(userID)
        getFollowing(userID)

        fabAdd.setOnClickListener {
            addFavorite(userModel)

        }

    }


    private fun getDetailUser(userID: String) {
        val service = ApiClient.service.getDetail(userID)
        service.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    initDetailUser(user)
                }
            }

            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {

            }

        })
    }

    private fun initDetailUser(user: UserModel?) {
        val sql = helper.queryByUsername(user?.userName)
        if (sql.count > 0) {
            detailFav.visibility = View.VISIBLE
        }
        userID.text = user?.userName
        nameDetail.text = user?.name
        valueFollowing.text = user?.totalFollowing.toString()
        valueFollower.text = user?.totalFollower.toString()
        valueRepo.text = user?.repository
        Glide.with(this).load(user?.imageUrl).transform(RoundedCorners(64)).into(imageDetail)
    }


    private fun getFollowers(user: String) {
        val call = ApiClient.service.getFollowers(user)
        call.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>, response: Response<List<UserModel>>
            ) {
                if (response.isSuccessful) {
                    pagerAdapter.addFragment(
                        FollowerFragment.newInstance(user), resources.getString(
                            R.string.follower
                        )
                    )

                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }

        })
    }


    private fun getFollowing(user: String) {
        val call = ApiClient.service.getFollowing(user)
        call.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>, response: Response<List<UserModel>>
            ) {
                if (response.isSuccessful) {
                    pagerAdapter.addFragment(
                        FollowingFragment.newInstance(user), resources.getString(
                            R.string.following
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }

        })
    }


    private fun addFavorite(user: UserModel?) {
        val sql = helper.queryByUsername(user?.userName)
        if (sql.count > 0) {
            showSnackbarMessage("Already exists")
        } else {
            val values = ContentValues()
            values.put(DatabaseContract.UserColumns.USERNAME, user?.userName)
            values.put(DatabaseContract.UserColumns.AVATAR, user?.imageUrl)

            val result = helper.insert(values)
            showResult(result)
            detailFav.visibility = View.VISIBLE
        }
    }

    private fun showResult(result: Long) {
        when {
            result > 0 -> {
                showSnackbarMessage("Save to Favorite !")
            }
            else -> {
                showSnackbarMessage("Failed to Favorite !")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rvFollower, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setLanguage(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences(langKey, Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("lang", "").toString()
        setLanguage(language)
    }


    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }


}