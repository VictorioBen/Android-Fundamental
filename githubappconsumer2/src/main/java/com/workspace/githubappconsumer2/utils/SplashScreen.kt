package com.workspace.githubappconsumer2.utils

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import androidx.appcompat.app.AppCompatActivity
import com.workspace.githubappconsumer2.R
import com.workspace.githubappconsumer2.activity.SearchActivity

import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    private lateinit var splash: Animation
    companion object{
        private const val SPLASH: Long = 3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splash = loadAnimation(this, R.anim.app_splash)
        imageLogo.startAnimation(splash)
      Handler(Looper.getMainLooper()).postDelayed({
          startActivity(Intent(this, SearchActivity::class.java))
          finish()
      }, SPLASH)


    }
}