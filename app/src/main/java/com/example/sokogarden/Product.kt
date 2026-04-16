package com.example.sokogarden
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
 
data class Product(
    val product_id: Int,
    val product_name: String,
    val product_description: String?,
    val product_cost: Int,
    val product_photo: String?
)
 
class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
 
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.product_name)
        val txtDesc: TextView = itemView.findViewById(R.id.product_description)
        val txtPrice: TextView = itemView.findViewById(R.id.product_cost)
        val imgProduct: ImageView = itemView.findViewById(R.id.product_photo)
        val btnPurchase: Button = itemView.findViewById(R.id.btnPurchase)
    }
    //Access the Layout - Single Item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return ProductViewHolder(view)
    }
 
    //Access Views in Single Item XML and Bind Data
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.txtName.text = product.product_name
        holder.txtDesc.text = product.product_description ?: "No description"
        holder.txtPrice.text = "Ksh ${product.product_cost}"
        
        val imageUrl = "https://cedric22a.alwaysdata.net/static/images/${product.product_photo}"

        Glide.with(holder.itemView.context)
            .load(imageUrl )
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgProduct)
 
        // 🚀 Open Product Details Activity
        holder.btnPurchase.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetails::class.java).apply {
                putExtra("product_id", product.product_id)
                putExtra("product_name", product.product_name)
                putExtra("product_description", product.product_description)
                putExtra("product_cost", product.product_cost)
                putExtra("product_photo", product.product_photo)
            }
            context.startActivity(intent)
        }
    }
 
    override fun getItemCount(): Int = productList.size

    companion object {
        fun fromJsonArray(jsonArray: JSONArray): List<Product> {
            val list = mutableListOf<Product>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Product(
                        product_id = obj.optInt("product_id", 0),
                        product_name = obj.optString("product_name", "N/A"),
                        product_description = obj.optString("product_description", ""),
                        product_cost = obj.optInt("product_cost", 0),
                        product_photo = obj.optString("product_photo", "")
                    )
                )
            }
            return list
        }
    }
}