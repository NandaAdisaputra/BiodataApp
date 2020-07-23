package com.qiswatululfah.biodataapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.qiswatululfah.biodataapp.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        val splashInterval = 5000
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val i = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                startActivity(i)
                this.finish()
            }

            private fun finish() {}
        }, splashInterval.toLong())
    }
}

