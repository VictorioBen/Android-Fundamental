@file:Suppress("DEPRECATION")

package com.workspace.githubusertwo.activity


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.workspace.githubusertwo.R
import com.workspace.githubusertwo.adapter.UserAdapter
import com.workspace.githubusertwo.model.UserModel
import com.workspace.githubusertwo.view.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import java.util.*
import kotlin.system.exitProcess


class SearchActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var searchView: SearchViewModel
    private val langKey: String = "settings"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        loadLocate()
        settings()
        recyclerUser()

        searchView = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)

        searchView.listResult.observe(this, { list: List<UserModel> ->
            adapter.addAll(list)
            showImage(false)
            loading(false)
            if (list.isEmpty()){
                showImage(true)
            }
        })

        search()

    }


    private fun recyclerUser() {
        adapter = UserAdapter(onClickListener = {
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_NAME, it)
            startActivity(intent)
        })
        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.setHasFixedSize(true)
        rvUser.adapter = adapter
    }

    private fun showImage(isVisible: Boolean) {
        imageEmpty.visibility = if (isVisible) {
            VISIBLE
        } else {
            GONE
        }
    }

    private fun loading(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) {
            VISIBLE
        } else {
            GONE
        }
    }


    private fun search() {
        tvFind.text = resources.getString(R.string.find_user)
        searchingUser.queryHint = resources.getString(R.string.find_user)
        searchingUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                loading(true)
                showImage(false)
                searchView.onResponse(query)
               showImage(false)
                searchingUser.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return false
            }

        })
    }


    private fun changeLanguage() {
        val listLanguage = arrayOf("Indonesia", "Spain", "Russia")
        val builder = AlertDialog.Builder(this@SearchActivity, R.style.AlertDialogCustom)
        builder.setTitle(resources.getString(R.string.language_change))
        builder.setSingleChoiceItems(listLanguage, -1) { dialog, i ->
            when (i) {
                0 -> {
                    setLanguage("in")
                    recreate()
                }
                1 -> {
                    setLanguage("ru")
                    recreate()
                }
                2 -> {
                    setLanguage("es")
                    recreate()
                }
            }
            dialog.dismiss()
        }
        val mDialog = builder.create()
        mDialog.show()
    }

    private fun setLanguage(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences(langKey, Context.MODE_PRIVATE).edit()
        editor.putString("lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences(langKey, Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("lang", "").toString()
        setLanguage(language)
    }

    @SuppressLint("InflateParams")
    private fun settings() {
        val bottomSheet = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheet.setContentView(view)
        settingLang.setOnClickListener {
            bottomSheet.show()
        }

        view.btnLanguage.setOnClickListener { changeLanguage()
            Toast.makeText(this, "change", Toast.LENGTH_SHORT).show()
            bottomSheet.dismiss()
        }

        view.btnFav.setOnClickListener {
            val intent = Intent(this, ListFavoriteActivity::class.java)
            startActivity(intent)
            bottomSheet.dismiss()
        }

        view.btnAlarm.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
            bottomSheet.dismiss()
        }

        view.btnExit.setOnClickListener {
            val builder = AlertDialog.Builder(this@SearchActivity)
            builder.setTitle(resources.getString(R.string.exit))
            builder.setMessage(resources.getString(R.string.sure))
            builder.setPositiveButton(resources.getString(R.string.exit)) { _, _ ->
                finish()
                exitProcess(0)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create()
            builder.show()
        }
    }




}
