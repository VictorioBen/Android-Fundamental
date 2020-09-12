package com.workspace.githubappconsumer2.activity.activity

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.workspace.githubappconsumer2.R
import com.workspace.githubappconsumer2.activity.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.workspace.githubconsumer2.adapter.UserFavAdapter
import com.workspace.githubappconsumer2.activity.helper.MappingHelper
import com.workspace.githubappconsumer2.activity.helper.UserHelper
import com.workspace.githubappconsumer2.activity.model.UserModel
import kotlinx.android.synthetic.main.activity_list_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class ListFavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: UserFavAdapter
    private lateinit var helper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_favorite)
        title = getString(R.string.favorite)
        helper = UserHelper.getInstance(applicationContext)
        helper.open()
        recyclerFavorite()
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                getListFavorite()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

    }

    private fun recyclerFavorite() {
        adapter = UserFavAdapter(onClickListener = { user: UserModel ->

            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_NAME, user)
            startActivity(intent)

        }, onLongClick = { user, position ->

            delete(user.userName.toString(), position)
        })

        rvFav.layoutManager = LinearLayoutManager(this)
        rvFav.setHasFixedSize(true)
        rvFav.adapter = adapter


    }

    private fun delete(user: String, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.delete))
        builder.setMessage(resources.getString(R.string.can_delete))
        builder.setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
            deleteItem(user, position)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create()
        builder.show()
    }


    private fun deleteItem(user: String, position: Int) {
        val result = contentResolver?.delete(CONTENT_URI, user, null)
        if (result == 0) {
            showSnackbarMessage("Failed Delete")
        } else {
            adapter.removeAt(position)
            showSnackbarMessage("Success Delete")
        }
    }

    private fun getListFavorite() {
        GlobalScope.launch(Dispatchers.Main) {
            barFav.visibility = View.VISIBLE
            val deferred = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            barFav.visibility = View.INVISIBLE
            val user: ArrayList<UserModel> = deferred.await()
            addUsersToAdapter(user)

        }
    }


    private fun addUsersToAdapter(notes: ArrayList<UserModel>) {
        when {
            notes.isNotEmpty() -> {
                adapter.addAll(notes)
            }
            else -> {
                adapter.addAll(emptyList())
                favEmpty.visibility = View.VISIBLE
                Toast.makeText(this, "EMPTY", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rvFav, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }
}