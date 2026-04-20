package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoContainer = findViewById<LinearLayout>(R.id.logo_container)
        
        // 💫 Animated "Fade In and Scale" for the logo
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 1500
        logoContainer.startAnimation(animation)

        // Delay for 3.5 seconds for a premium feel
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            // Custom transition between screens
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 3500)
    }
}