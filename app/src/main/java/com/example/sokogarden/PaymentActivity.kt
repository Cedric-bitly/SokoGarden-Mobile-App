package com.example.sokogarden

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the views by their IDs
        val txtName = findViewById<TextView>(R.id.txtProductName)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val txtCost = findViewById<TextView>(R.id.txtProductCost)
        val phoneInput = findViewById<TextInputEditText>(R.id.phone)
        val btnPay = findViewById<MaterialButton>(R.id.pay)

        // Retrieve the Data passed from the previous Activity
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val photo = intent.getStringExtra("product_photo")

        // Bind the data to the views
        txtName.text = name
        txtCost.text = "Amount: Ksh $cost"

        val imageUrl = "https://cedric22a.alwaysdata.net/static/images/$photo"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgProduct)

        // Handle the Pay Now button click
        btnPay.setOnClickListener {
//            Specify the api endpoint for making payment
            val api = "https://cedric22a.alwaysdata.net/api/make_payment"
            
//            create request params
           val data = RequestParams()

//            insert data into request params
            data.put("phone", phoneInput.text.toString())


            val phoneNum = phoneInput.text.toString()
            if (phoneNum.isEmpty()) {
                phoneInput.error = "Please enter your phone number"
            } else {
                Toast.makeText(this, "Processing payment for $name via $phoneNum", Toast.LENGTH_LONG).show()
                
                val helper = ApiHelper(applicationContext)
                helper.post(api, data)
            }
        }
    }
}