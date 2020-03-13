package com.thakurintl.uizprestashopnew.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import com.thakurintl.uizprestashopnew.viewmodels.Order
import kotlinx.android.synthetic.main.order_layout_single.view.*

class OrderRecyclerAdapter(
    private val context: Context,
    private val itemList: List<Order>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_layout_single, parent, false)

        return OrderItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OrderItemViewHolder -> {
                holder.bindView(itemList[position], orderClickListener!!)
            }

        }

    }

    interface OrderClickListener {
        fun onClicked(order: Order)
    }

    private var orderClickListener: OrderClickListener? = null

    fun setOrderClickListener(orderClickListener: OrderClickListener) {
        this.orderClickListener = orderClickListener
    }


    class OrderItemViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindView(order: Order, orderClickListener: OrderClickListener) {

            itemView.order_product_name.text =
                UtilityObjects.truncateProductName(order.associations.order_rows[0].product_name)
            itemView.order_date.text = order.date_add.take(10)
            itemView.order_order_price.text =
                "$" + UtilityObjects.truncateDecimal(order.total_paid.toDouble(), 2)
            itemView.order_payment_method.text = order.payment

            itemView.status_layout.setCardBackgroundColor(Color.parseColor(order.order_status.color))
            itemView.order_status.text = order.order_status.name


            itemView.product_list_card_view.setOnClickListener {
                orderClickListener!!.onClicked(order)
            }

        }

    }

}

