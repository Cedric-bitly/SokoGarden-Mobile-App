package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val signinBtn = findViewById<Button>(R.id.signinBtn)
        val aboutBtn = findViewById<Button>(R.id.aboutBtn)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressbar)

        // Navigation
        signupBtn.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        signinBtn.setOnClickListener {
            startActivity(Intent(this, Signin::class.java))
        }

        aboutBtn.setOnClickListener {
            startActivity(Intent(this, About::class.java))
        }

        // Load Products
        val api = "https://cedric22a.alwaysdata.net/api/products"
        val helper = ApiHelper(this)
        helper.loadProducts(api, recyclerView, progressBar)
    }
}