package com.thakurintl.uizprestashopnew.activity

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Toolbar setup
        setSupportActionBar(search_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        //Hide empty list
        if (search_empty_list_view.isVisible)
            search_empty_list_view.visibility = View.GONE


        edt_txt_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    Log.i("FFFF", "Msg:   ${edt_txt_search.text}")

                    searchProductOnQuery(edt_txt_search.text)

                    return true
                }

                return false

            }

        })

//        edt_txt_search.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//
//                if (event!!.action == KeyEvent.ACTION_DOWN && event.action == KeyEvent.KEYCODE_ENTER) {
//
//                    Log.i("FFFF", "Msg:   ${edt_txt_search.text}")
//
//                    return true
//                }
//
//                return false
//            }
//
//        })


//        edt_txt_search.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                //Log.i("FFFFF", "Changed Text: $s")
//
//            }
//
//        })

    }

    private fun searchProductOnQuery(text: Editable?) {

        val formattedSearchString = "%[$text]%"

        val services = ApiClient(this@SearchActivity).getClient()!!.create(ApiServices::class.java)

        services.searchItem("full", "JSON", UtilityObjects.API_KEY, "50", formattedSearchString)
            .enqueue(object : Callback<ProductListModel> {
                override fun onFailure(call: Call<ProductListModel>, t: Throwable) {

                    Log.i("FFFF", "Failed:  $t")
                    //Show empty list

                    if (search_item_recycler_view.isVisible)
                        search_item_recycler_view.visibility = View.GONE

                    if (!search_empty_list_view.isVisible)
                        search_empty_list_view.visibility = View.VISIBLE

                }

                override fun onResponse(
                    call: Call<ProductListModel>,
                    response: Response<ProductListModel>
                ) {

                    if (response.isSuccessful) {

                        val searchProduct = response.body()

                        if (searchProduct!!.products.isNotEmpty()) {
                            Log.i("FFFF", "Size:  ${searchProduct.products.size}")

                            displayDatainRecyclerView(searchProduct.products)

                            if (!search_item_recycler_view.isVisible)
                                search_item_recycler_view.visibility = View.VISIBLE

                            if (search_empty_list_view.isVisible)
                                search_empty_list_view.visibility = View.GONE
                        }


                    }

                }

            })


    }

    private fun displayDatainRecyclerView(productList: List<Product>) {

        val productAdapter = AllProductRecyclerAdapter(productList)

//        val gridLayoutManager = GridLayoutManager(this, 2)
//        search_item_recycler_view.layoutManager = gridLayoutManager

//        search_item_recycler_view.addItemDecoration(
//            UtilityObjects.GridSpacingItemDecoration(
//                2,
//                UtilityObjects.dpToPx(10, this),
//                true
//            )
//        )

//        search_item_recycler_view.itemAnimator = DefaultItemAnimator()
        search_item_recycler_view.layoutManager = LinearLayoutManager(this)
        search_item_recycler_view.adapter = productAdapter

        productAdapter.setOnItemClickListener(object : AllProductRecyclerAdapter.ItemClickListener {
            override fun onItemClick(productId: Int, productName: String) {

                Log.i("&&&&", "item clicked all Search activity product ID:  $productId")
            }

        })

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)

    }

}

