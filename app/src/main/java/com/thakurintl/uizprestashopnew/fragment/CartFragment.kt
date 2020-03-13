package com.thakurintl.uizprestashopnew.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.activity.LoginSignupActivity
import com.thakurintl.uizprestashopnew.adapter.CartRecyclerAdapter
import com.thakurintl.uizprestashopnew.dataclass.*
import com.thakurintl.uizprestashopnew.objects.CartDetailsHelperClass
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import kotlinx.android.synthetic.main.fragment_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment() {

    //To save client details in sharedPreference
    private val loginSharedPrefName: String = "clientDetailsPref"

    //Global Variable
    var productList = mutableListOf<Product>()

    var cartList: MutableList<CartRow> = mutableListOf()

    //Adapter
    private lateinit var cartRecyclerAdapter: CartRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        if (MySharedPreferences.getLoginStatus(context!!.applicationContext)) {

            //Getting Cart Lists
            //getCartOfUser()

            //Getting details of the product which is listed in the cart
            //getProductDetails(cartList)

            /**
             * Using CartDetailsHelperClass
             * filter carts and return products
             * details of the products which are
             * in carts
             */

            val cartDetailsHelperClass = CartDetailsHelperClass()

            cartDetailsHelperClass.getProductsfromCart(
                context!!,
                context?.getSharedPreferences(
                    loginSharedPrefName,
                    Context.MODE_PRIVATE
                )?.getInt("id", 0)!!
            )

            cartDetailsHelperClass.setOnCartDetailSuccessListener(object :
                CartDetailsHelperClass.CartDetailSuccessCallBack {
                override fun onSuccess(cartList: MutableList<CartRow>) {
                    Log.i("CCAARRTT", "cartList : $cartList")
                }
            })
        } else {
            startActivity(Intent(context, LoginSignupActivity::class.java))
        }
        return view
    }

    private fun getCartOfUser() {
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

                        for (i in 0 until cartSize) {

                            //val cartId = cartListFull.carts[i].id

                            val idCustomer = cartListFull.carts[i].id_customer

                            if (context?.getSharedPreferences(
                                    loginSharedPrefName,
                                    Context.MODE_PRIVATE
                                )?.getInt("id", 0) == idCustomer.toInt()
                            ) {
                                //Another loop is necessary
                                val cartRowLength =
                                    cartListFull.carts[i].associations.cart_rows.size

                                for (j in 0 until cartRowLength) {
                                    cartList.add(cartListFull.carts[i].associations.cart_rows[j])
                                    //cartList[j].cart_id = cartId

                                    Log.i(
                                        "QQQQQ",
                                        "DATA:  ${cartListFull.carts[i].associations.cart_rows[j]}"
                                    )
                                }

                            } else
                                Log.i("KKKKK", "SAAADDDDDDD NOOOOT matched1!!!!!!!!!!!!!!!!!!")
                        }
                    }
                }
            }
        })
    }

    private fun getProductDetails(cartList: List<CartRow>) {

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

                    initRecyclerView()
                    addDataSet(productList)

                }
            }
        })
    }

    private fun getStockAvailable(productlst: List<Product>) {

        Log.i("777777", "getStockAvailable() is called")

        val services = ApiClient(context!!).getClient()?.create(ApiServices::class.java)
        val call = services!!.getStockAvailableDetails(
            "full",
            "JSON",
            context!!.getString(R.string.API_KEY)
        )

        call.enqueue(object : Callback<StocksAvailableListModel> {
            override fun onFailure(call: Call<StocksAvailableListModel>, t: Throwable) {
                Log.i("777777", "Throwable:  $t")
            }

            override fun onResponse(
                call: Call<StocksAvailableListModel>,
                response: Response<StocksAvailableListModel>
            ) {

                if (response.isSuccessful) {

                    val availableDetails = response.body()!!
                    val size = availableDetails.stock_availables.size

                    for (i in 0 until size) {

                        for (j in 0 until productlst.size) {
                            Log.i("*******", "Second Loop")
                            if (productlst[j].associations.stock_availables[0].id ==
                                availableDetails.stock_availables[i].id.toString()
                            ) {
                                productList[j].stockQuantityAvailable =
                                    availableDetails.stock_availables[i].quantity

                            }

                        }

                    }

                    initRecyclerView()
                    addDataSet(productList)

                }

            }

        })

    }

    private fun addDataSet(productList: MutableList<Product>) {
        cartRecyclerAdapter.submitData(productList, context!!)

    }

    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {

        cart_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context!!)
            cartRecyclerAdapter = CartRecyclerAdapter()
            adapter = cartRecyclerAdapter
        }

    }

}

