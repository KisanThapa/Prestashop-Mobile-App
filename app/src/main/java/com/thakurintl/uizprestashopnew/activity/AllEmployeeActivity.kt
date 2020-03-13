package com.thakurintl.uizprestashopnew.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.adapter.AllProductRecyclerAdapter
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.dataclass.ProductListModel
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import kotlinx.android.synthetic.main.activity_all_employee.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllEmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_employee)

        //Toolbar
        setSupportActionBar(all_employee_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        if (!product_list_progress_bar.isVisible)
            product_list_progress_bar.visibility = View.VISIBLE

        /**
         * get all products to display
         * in recycler view
         * */
        getAllProducts()

    }

    private fun getAllProducts() {

        val services = ApiClient(applicationContext).getClient()!!.create(ApiServices::class.java)

        val call = services.getAllProductList("full", "JSON", UtilityObjects.API_KEY)

        call.enqueue(object : Callback<ProductListModel> {
            override fun onFailure(call: Call<ProductListModel>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ProductListModel>,
                response: Response<ProductListModel>
            ) {

                if (response.isSuccessful) {

                    val allProductListModel = response.body()
                    Log.i("*******", "Product:  $allProductListModel")

                    displayDatainRecyclerView(allProductListModel!!.products)
                }

            }

        })

    }

    private fun displayDatainRecyclerView(productList: List<Product>) {

        if (product_list_progress_bar.isVisible)
            product_list_progress_bar.visibility = View.GONE

        val productAdapter = AllProductRecyclerAdapter(productList)

        product_list_recycler_view.layoutManager = LinearLayoutManager(this)
        product_list_recycler_view.adapter = productAdapter

        productAdapter.setOnItemClickListener(object : AllProductRecyclerAdapter.ItemClickListener {
            override fun onItemClick(productId: Int, productName: String) {

                val intent = Intent(this@AllEmployeeActivity, ProductItemActivity::class.java)
                intent.putExtra("PID", productId)
                intent.putExtra("PNAME", productName)
                startActivity(intent)
            }

        })

    }

}
