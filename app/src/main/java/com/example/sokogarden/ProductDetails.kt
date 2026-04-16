package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class ProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Find views
        val detailPhoto = findViewById<ImageView>(R.id.detail_photo)
        val detailName = findViewById<TextView>(R.id.detail_name)
        val detailCost = findViewById<TextView>(R.id.detail_cost)
        val detailDescription = findViewById<TextView>(R.id.detail_description)
        val btnMakePurchase = findViewById<MaterialButton>(R.id.btnMakePurchase)

        // Get data from Intent
        val id = intent.getIntExtra("product_id", 0)
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val desc = intent.getStringExtra("product_description")
        val photo = intent.getStringExtra("product_photo")

        // Bind data
        detailName.text = name
        detailCost.text = "Ksh $cost"
        detailDescription.text = desc

        val imageUrl = "https://cedric22a.alwaysdata.net/static/images/$photo"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(detailPhoto)

        // Logic for Make Purchase button - Now opens PaymentActivity
        btnMakePurchase.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("product_id", id)
                putExtra("product_name", name)
                putExtra("product_cost", cost)
                putExtra("product_photo", photo) // 👈 This was missing!
            }
            startActivity(intent)
        }
    }
}