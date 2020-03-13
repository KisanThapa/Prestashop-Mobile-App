package com.thakurintl.uizprestashopnew.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.adapter.OrderRecyclerAdapter
import com.thakurintl.uizprestashopnew.customviews.OrderDetailBottomSheet
import com.thakurintl.uizprestashopnew.objects.OrderDetailsHelperClass
import com.thakurintl.uizprestashopnew.viewmodels.Order
import kotlinx.android.synthetic.main.activity_orders_history.*


class OrdersHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_history)

        //Toolbar setup
        setSupportActionBar(orders_history_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        getOrderHistory()

    }

    private fun getOrderHistory() {

        val orderDetailsHelperClass = OrderDetailsHelperClass()

        orderDetailsHelperClass.getOrderDetails(this)

        orderDetailsHelperClass.setOnOrderDetailSuccessListener(object :
            OrderDetailsHelperClass.OrderDetailSuccessCallBack {
            override fun onSuccess(orderList: MutableList<Order>) {

                if (!orderList.isNullOrEmpty())
                    initRecyclerView(orderList)

            }

        })

    }

    private fun initRecyclerView(orderList: MutableList<Order>) {

        orderList.reverse()
        val cartAdapter = OrderRecyclerAdapter(this@OrdersHistoryActivity, orderList)
        cartAdapter.setOrderClickListener(object : OrderRecyclerAdapter.OrderClickListener {
            override fun onClicked(order: Order) {
                Log.i("ZZZZZ", "order: $order")

                //Calling Bottom Sheet
                val orderDetailBottomSheet = OrderDetailBottomSheet(order)
                val orderDetailSheet = orderDetailBottomSheet.newInstance()
                orderDetailSheet.show(supportFragmentManager, orderDetailSheet.tag)

            }
        })
        order_recycler_layout.layoutManager =
            LinearLayoutManager(this@OrdersHistoryActivity)
        order_recycler_layout.adapter = cartAdapter

        if (order_progressBar.isVisible)
            order_progressBar.visibility = View.GONE

    }

}
