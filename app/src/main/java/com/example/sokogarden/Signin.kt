package com.example.sokogarden

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.CycleInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams


class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the views by their IDs
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinBtn = findViewById<Button>(R.id.signinBtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

        // Set listener to navigate to Signup activity
        signupTextView.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            finish() 
        }


        // Logic for sign-in button
        signinBtn.setOnClickListener {
            val emailStr = email.text.toString().trim()
            val passStr = password.text.toString()

            // Check if fields are empty
            if (emailStr.isEmpty() || passStr.isEmpty()) {
                // Playful "Shake" animation
                val shake = ObjectAnimator.ofFloat(signinBtn, "translationX", 0f, 25f)
                shake.duration = 500
                shake.interpolator = CycleInterpolator(3f)
                shake.start()

                // Set errors on fields
                if (emailStr.isEmpty()) email.error = "Required"
                if (passStr.isEmpty()) password.error = "Required"
                
            } else {
                // Proceed with API logic
                val api = "https://cedric22a.alwaysdata.net/api/signin"
                val data = RequestParams()
                data.put("email", emailStr)
                data.put("password", passStr)

                val helper = ApiHelper(applicationContext)
                // Pass the button and password field to the helper for the "Trap" logic
                helper.post_login(api, data, signinBtn, password)
            }
        }
    }
}