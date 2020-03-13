package com.thakurintl.uizprestashopnew.objects

import android.content.Context
import android.util.Log
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.dataclass.AddressListResponseModel
import com.thakurintl.uizprestashopnew.modelclass.OrderStatusModel
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import com.thakurintl.uizprestashopnew.viewmodels.Order
import com.thakurintl.uizprestashopnew.viewmodels.OrdersModelClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsHelperClass {


    private var orderDetailSuccessCallBack: OrderDetailSuccessCallBack? = null
    private val loginSharedPrefName: String = "clientDetailsPref"
    private var filteredOrders = mutableListOf<Order>()


    interface OrderDetailSuccessCallBack {
        fun onSuccess(orderList: MutableList<Order>)
    }

    fun setOnOrderDetailSuccessListener(orderDetailSuccessCallBack: OrderDetailSuccessCallBack) {
        if (this.orderDetailSuccessCallBack == null)
            this.orderDetailSuccessCallBack = orderDetailSuccessCallBack
    }

    fun getOrderDetails(
        context: Context
    ) {
        getStatusDetails(context)
        getOrderAddresses(context)

        val services = ApiClient(context).getClient()?.create(ApiServices::class.java)

        val call = services!!.getOrderList("full", "JSON", context.getString(R.string.API_KEY))
        call.enqueue(object : Callback<OrdersModelClass> {
            override fun onFailure(call: Call<OrdersModelClass>, t: Throwable) {}

            override fun onResponse(
                call: Call<OrdersModelClass>,
                response: Response<OrdersModelClass>
            ) {
                if (response.isSuccessful) {
                    val modelClass = response.body()
                    Log.i("LLLLLL", "modelClass:  $modelClass")

                    for (i in modelClass!!.orders.indices) {

                        if (modelClass.orders[i].id_customer.toInt() == context.getSharedPreferences(
                                loginSharedPrefName, Context.MODE_PRIVATE
                            ).getInt("id", 0)
                        ) {
                            filteredOrders.add(modelClass.orders[i])
                        }
                    }

                    for (i in filteredOrders.indices) {

                        for (j in orderStatusModel.order_states.indices) {
                            Log.i("EEEEEE", "ID: ${filteredOrders[i].current_state.toInt()}")
                            if (filteredOrders[i].current_state.toInt() == orderStatusModel.order_states[j].id) {
                                filteredOrders[i].order_status = orderStatusModel.order_states[j]
                            }
                        }
                        for (j in orderAddresses.addresses.indices) {
                            if (filteredOrders[i].id_address_delivery.toInt() == orderAddresses.addresses[j].id) {
                                filteredOrders[i].address_delivery = orderAddresses.addresses[j]
                            }
                        }
                        for (j in orderAddresses.addresses.indices) {
                            if (filteredOrders[i].id_address_invoice.toInt() == orderAddresses.addresses[j].id) {
                                filteredOrders[i].address_invoice = orderAddresses.addresses[j]
                            }
                        }

                    }
                    orderDetailSuccessCallBack!!.onSuccess(filteredOrders)
                }

            }

        })

    }

    lateinit var orderStatusModel: OrderStatusModel

    private fun getStatusDetails(context: Context) {
        val services = ApiClient(context).getClient()?.create(ApiServices::class.java)

        val call = services!!.getOrderStatus("full", "JSON", context.getString(R.string.API_KEY))
        call.enqueue(object : Callback<OrderStatusModel> {
            override fun onFailure(call: Call<OrderStatusModel>, t: Throwable) {}

            override fun onResponse(
                call: Call<OrderStatusModel>,
                response: Response<OrderStatusModel>
            ) {
                if (response.isSuccessful) {
                    orderStatusModel = response.body()!!
                }
            }
        })

    }


    lateinit var orderAddresses: AddressListResponseModel

    private fun getOrderAddresses(context: Context) {
        val services = ApiClient(context).getClient()?.create(ApiServices::class.java)

        val call = services!!.getAddressList("full", "JSON", context.getString(R.string.API_KEY))
        call.enqueue(object : Callback<AddressListResponseModel> {
            override fun onFailure(call: Call<AddressListResponseModel>, t: Throwable) {}
            override fun onResponse(
                call: Call<AddressListResponseModel>,
                response: Response<AddressListResponseModel>
            ) {
                if (response.isSuccessful) {
                    orderAddresses = response.body()!!
                }
            }
        })
    }
}