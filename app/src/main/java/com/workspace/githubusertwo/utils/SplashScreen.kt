package com.workspace.githubusertwo.utils

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import androidx.appcompat.app.AppCompatActivity
import com.workspace.githubusertwo.R
import com.workspace.githubusertwo.R.anim.app_splash
import com.workspace.githubusertwo.activity.SearchActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    private lateinit var splash: Animation
    companion object{
        private const val SPLASH: Long = 3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splash = loadAnimation(this, app_splash)
        imageLogo.startAnimation(splash)
      Handler(Looper.getMainLooper()).postDelayed({
          startActivity(Intent(this, SearchActivity::class.java))
          finish()
      }, SPLASH)


    }
}