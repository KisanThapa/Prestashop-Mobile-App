package com.thakurintl.uizprestashopnew.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import kotlinx.android.synthetic.main.home_product_list_single_item.view.*

class HomeProductRecyclerAdapter(

    private val context: Context,

    private val itemList: List<Product>

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_product_list_single_item, parent, false)

        return AllProductViewHolder(view)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is AllProductViewHolder -> {
                holder.bindView(itemList[position])
            }
        }

    }

    class AllProductViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        @SuppressLint("SetTextI18n")
        fun bindView(productList: Product) {

            itemView.home_product_list_title.text = productList.name
            itemView.home_product_list_price.text = "$" +
                    UtilityObjects.truncateDecimal(productList.price.toDouble(), 2).toString()

            //Log.i("+++++", "ID:  ${productList.associations}")

            if (productList.associations.images.isNullOrEmpty()) {
                itemView.home_product_list_thumbnail.setImageResource(R.drawable.ic_placeholder)
            } else {
                val imgUrl =
                    "http://t.uiz.eu/api/images/products/" + productList.id + "/" + productList.associations.images[0].id + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"

                Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemView.home_product_list_thumbnail)

            }
            itemView

        }

    }

}

