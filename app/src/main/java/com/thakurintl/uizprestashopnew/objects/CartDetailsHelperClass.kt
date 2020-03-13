package com.thakurintl.uizprestashopnew.objects

import android.content.Context
import android.util.Log
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.dataclass.CartListResponseModel
import com.thakurintl.uizprestashopnew.dataclass.CartRow
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.dataclass.ProductListModel
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartDetailsHelperClass {

    private var cartDetailSuccessCallBack: CartDetailSuccessCallBack? = null

    interface CartDetailSuccessCallBack {
        fun onSuccess(cartList: MutableList<CartRow>)
    }

    fun setOnCartDetailSuccessListener(cartDetailSuccessCallBack: CartDetailSuccessCallBack) {
        if (this.cartDetailSuccessCallBack == null)
            this.cartDetailSuccessCallBack = cartDetailSuccessCallBack
    }

    //Global Variable
    var productList = mutableListOf<Product>()

    //var cartList: MutableList<CartRow> = mutableListOf()

    fun getProductsfromCart(
        context: Context,
        userID: Int
    ) {
        val services = ApiClient(context!!).getClient()?.create(ApiServices::class.java)
        val call = services!!.getCartList("full", "JSON", context!!.getString(R.string.API_KEY))

        call.enqueue(object : Callback<CartListResponseModel> {
            override fun onFailure(call: Call<CartListResponseModel>, t: Throwable) {
                Log.i("KKKKKK", "Throwable:  $t")
            }

            override fun onResponse(
                call: Call<CartListResponseModel>,
                response: Response<CartListResponseModel>
            ) {
                if (response.isSuccessful) {

                    val cartListFull = response.body()
                    if (cartListFull != null) {

                        val cartSize = cartListFull.carts.size
                        Log.i("CCAAA", "cartListFull:  $cartListFull")

                        for (i in 0 until cartSize) {

//                            val cartId = cartListFull.carts[i].id

//                            val idCustomer = cartListFull.carts[i].id_customer
//
//                            if (userID == idCustomer.toInt()) {
//                                //Another loop is necessary
//                                val cartRowLength =
//                                    cartListFull.carts[i].associations.cart_rows.size
//
//                                val cartList: MutableList<CartRow> = mutableListOf()
//                                for (j in 0 until cartRowLength) {
//
//
//                                    cartList.add(cartListFull.carts[i].associations.cart_rows[j])
//                                    cartList[j].cart_id = cartId
//                                }
//
//                                cartDetailSuccessCallBack!!.onSuccess(cartList)
//
//                            } else
//                                Log.i("KKKKK", "SAAADDDDDDD NOOOOT matched1!!!!!!!!!!!!!!!!!!")
                        }
                    }
                }
            }
        })
    }

    private fun getProductDetails(context: Context, cartList: List<CartRow>) {

        val services = ApiClient(context!!).getClient()?.create(ApiServices::class.java)
        val callProduct = services?.getProductList(
            "full",
            "JSON",
            context!!.getString(R.string.API_KEY)
        )
        callProduct?.enqueue(object : Callback<ProductListModel> {

            override fun onFailure(
                call: Call<ProductListModel>,
                t: Throwable
            ) {
                Log.i("88888888888888", "Throwable:  $t")
            }

            override fun onResponse(
                call: Call<ProductListModel>,
                response: Response<ProductListModel>
            ) {
                if (response.isSuccessful) {

                    val productListFull = response.body()

                    val productSize = productListFull!!.products.size

                    for (i in 0 until productSize) {

                        for (j in cartList.indices) {

                            if (cartList[j].id_product.toInt() == productListFull.products[i].id) {

                                //Setting number of cart in cartQuantity
                                productListFull.products[i].cartQuantity = cartList[j].quantity

                                val stockAvailableId =
                                    productListFull.products[i].associations.stock_availables[0].id

                                productList.add(productListFull.products[i])


                            }
                        }
                    }

//                    initRecyclerView()
//                    addDataSet(productList)

                }
            }
        })
    }

}