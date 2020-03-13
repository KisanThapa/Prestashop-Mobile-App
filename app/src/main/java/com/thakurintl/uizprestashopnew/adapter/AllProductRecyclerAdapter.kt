package com.thakurintl.uizprestashopnew.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import kotlinx.android.synthetic.main.product_list_single_item.view.*

class AllProductRecyclerAdapter(
    /* private val context: Context,*/
    private val itemList: List<Product>

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_list_single_item, parent, false)

        return AllProductViewHolder(view)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is AllProductViewHolder -> {

                holder.bindView(itemList[position], itemClickListener)
            }
        }
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setOnItemClickListener(itemClickListener: ItemClickListener) {

        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(productId: Int, productName: String)
    }

    class AllProductViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindView(productList: Product, itemClickListener: ItemClickListener) {

            itemView.product_list_title.text = productList.name
            itemView.product_list_price.text = "$" +
                    UtilityObjects.truncateDecimal(productList.price.toDouble(), 2).toString()

            //Log.i("+++++", "ID:  ${productList.associations}")

            if (productList.associations.images.isNullOrEmpty()) {
                itemView.product_list_thumbnail.setImageResource(R.drawable.ic_placeholder)
            } else {
                val imgUrl =
                    "http://t.uiz.eu/api/images/products/" + productList.id + "/" + productList.associations.images[0].id + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"

                Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemView.product_list_thumbnail)

            }

            itemView.product_list_card_view.setOnClickListener {
                //Log.i("&&&&", "Card Clicked:")

                itemClickListener.onItemClick(productList.id, productList.name)
            }

            itemView.product_list_thumbnail.setOnClickListener {
                //Log.i("&&&&", "Picture Clicked:")

                itemClickListener.onItemClick(productList.id, productList.name)

            }

        }

    }

}

