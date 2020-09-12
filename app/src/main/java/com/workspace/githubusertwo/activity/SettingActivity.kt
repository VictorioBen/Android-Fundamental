package com.workspace.githubusertwo.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.workspace.githubusertwo.R
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*
import kotlin.system.exitProcess

class SettingActivity : AppCompatActivity() {
    private val langKey: String = "settings"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        title = getString(R.string.setting)
        loadLocate()
        favorite()
        exit()
        changeLanguage()
        alarm()

    }



    private fun changeLanguage() {
        settingLanguage.setOnClickListener {
            val listLanguage = arrayOf("Indonesia", "Spain", "Russia")
            val builder = AlertDialog.Builder(this@SettingActivity, R.style.AlertDialogCustom)
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
    }

    private fun favorite(){
        settingFav.setOnClickListener {
            startActivity(Intent(this, ListFavoriteActivity::class.java))
            finish()
        }
    }

    private fun alarm() {
        settingAlarm.setOnClickListener {
            startActivity(Intent(this, AlarmActivity::class.java))
            finish()
        }
    }
    private fun exit(){
        settingExit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.exit))
            builder.setMessage(resources.getString(R.string.sure))
            builder.setPositiveButton(
                resources.getString(R.string.exit)
            ) { _, _ ->
                moveTaskToBack(true);
                exitProcess(0)

            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create()
            builder.show()
        }

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



}