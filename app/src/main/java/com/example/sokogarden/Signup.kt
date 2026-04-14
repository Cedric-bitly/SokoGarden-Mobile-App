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

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the views by their IDs
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val signinTextView = findViewById<TextView>(R.id.signintxt)

        // Set listener to navigate back to Signin activity
        signinTextView.setOnClickListener {
            val intent = Intent(this, Signin::class.java)
            startActivity(intent)
            finish()
        }

        // Logic for sign-up button
        signupBtn.setOnClickListener {
            val userStr = username.text.toString()
            val emailStr = email.text.toString()
            val phoneStr = phone.text.toString()
            val passStr = password.text.toString()

            // Check if fields are empty
            if (userStr.isEmpty() || emailStr.isEmpty() || phoneStr.isEmpty() || passStr.isEmpty()) {
                // Playful "Shake" animation
                val shake = ObjectAnimator.ofFloat(signupBtn, "translationX", 0f, 25f)
                shake.duration = 500
                shake.interpolator = CycleInterpolator(3f)
                shake.start()
                
                // Optional: set errors on fields
                if (userStr.isEmpty()) username.error = "Required"
                if (emailStr.isEmpty()) email.error = "Required"
                if (phoneStr.isEmpty()) phone.error = "Required"
                if (passStr.isEmpty()) password.error = "Required"

            } else {
                // Proceed with API logic
                val api = "https://kbenkamotho.alwaysdata.net/api/signup"
                val data = RequestParams()
                data.put("username", userStr)
                data.put("email", emailStr)
                data.put("phone", phoneStr)
                data.put("password", passStr)

                // This part was missing/incomplete:
                val helper = ApiHelper(applicationContext)
                helper.post(api, data)


//                clear the details
                username.text.clear()
                email.text.clear()
                phone.text.clear()
                password.text.clear()
            }
                
                // TODO: Execute the POST request
            }
        }
    }
