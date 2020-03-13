package com.thakurintl.uizprestashopnew.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.customviews.DeleteAlertDialog
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import kotlinx.android.synthetic.main.layout_cart_list_item_view.view.*

class CartRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Product> = ArrayList()
    lateinit var context: Context

    var totalAmount: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_cart_list_item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CartViewHolder -> {
                holder.bindView(items[position], context)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitData(product: List<Product>, context: Context) {
        items = product
        this.context = context

        //setting number of carts in MySharedPreference
        MySharedPreferences.setCartLength(context, items.size)
        Log.i("88888", "Special ${items.size}")

        for (i in items.indices) {

            val temp = items[i].price.toDouble() * items[i].quantity.toDouble()
            totalAmount += temp
        }
        UtilityObjects.totalCartAmount = totalAmount
    }

    class CartViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val txtCartProductName = itemView.cart_product_name
        private val txtCartProductPrice = itemView.cart_product_price
        private val imgCartProduct = itemView.cart_list_product_image
        private val txtCartProductSize = itemView.txt_cart_product_size
        private val txtCartProductQuantity = itemView.txt_cart_product_quantity
        private val spnrCartProductStock = itemView.spinner_stock_available

        private val cartItemLayout = itemView.cart_item_layout


        @SuppressLint("SetTextI18n")
        fun bindView(product: Product, context: Context) {

            txtCartProductName.text = product.name

            txtCartProductPrice.text = "$" +
                    UtilityObjects.truncateDecimal(
                        product.price.toDouble(),
                        2
                    ) * product.cartQuantity.toBigDecimal()

            txtCartProductQuantity.text = product.cartQuantity

            txtCartProductSize.text = product.stockQuantityAvailable


            if (product.associations.images.isNullOrEmpty()) {
                imgCartProduct.setImageResource(R.drawable.ic_person_outline_account)
            } else {
                val imgUrl =
                    "http://t.uiz.eu/api/images/products/" + product.id + "/" + product.associations.images[0].id + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"

                Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_person_outline_account)
                    .into(imgCartProduct)

            }

            cartItemLayout.setOnClickListener {

                showAlertDialog(context)
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()

            }

            cartItemLayout.setOnLongClickListener {
                //Toast.makeText(context, "LONG CLICKED", Toast.LENGTH_LONG).show()

                showAlertDialog(context)

                true
            }


        }

        private fun showAlertDialog(context: Context) {

            val deleteAlertDialog = DeleteAlertDialog(context)
            deleteAlertDialog.showDialog("this is Dialog")

            deleteAlertDialog.setAlertDialogListener(object :
                DeleteAlertDialog.AlertDialogListener {
                override fun onEditButtonClicked() {

                    Toast.makeText(context, "Edit ButtonClicked", Toast.LENGTH_SHORT).show()

                }

                override fun onDeleteButtonClicked() {

                    Toast.makeText(context, "Delete ButtonClicked", Toast.LENGTH_SHORT).show()

                }
            })
        }


    }


}
