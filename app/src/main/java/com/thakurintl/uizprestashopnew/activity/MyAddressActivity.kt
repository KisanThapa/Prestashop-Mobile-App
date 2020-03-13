package com.thakurintl.uizprestashopnew.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.adapter.AddressRecyclerAdapter
import com.thakurintl.uizprestashopnew.dataclass.AddressListResponseModel
import com.thakurintl.uizprestashopnew.dataclass.Addresse
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_my_address.*
import retrofit2.Call
import retrofit2.Response

class MyAddressActivity : AppCompatActivity() {


    //To save client details in sharedPreference
    private val loginSharedPrefName: String = "clientDetailsPref"

    //Address Adapter define
    lateinit var addressAdapter: AddressRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_address)

        //Toolbar setup
        setSupportActionBar(my_address_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (!address_progressBar.isVisible)
            address_progressBar.visibility = View.VISIBLE

        getAddress()

    }

    private fun getAddress() {

        val services = ApiClient(this).getClient()?.create(ApiServices::class.java)

        val call = services!!.getAddressList("full", "JSON", getString(R.string.API_KEY))

        call.enqueue(object : retrofit2.Callback<AddressListResponseModel> {
            override fun onFailure(call: Call<AddressListResponseModel>, t: Throwable) {

                //Hide Progress Bar
                if (address_progressBar.isVisible)
                    address_progressBar.visibility = View.GONE


                Toasty.error(this@MyAddressActivity, "Something wrong!", Toast.LENGTH_SHORT, true)
                    .show()
                Log.i("KKKKKK", "Throwable:  $t")
            }

            override fun onResponse(
                call: Call<AddressListResponseModel>,
                response: Response<AddressListResponseModel>
            ) {
                val addresslist_full = response.body()

                if (addresslist_full != null) {

                    val addressSize = addresslist_full.addresses.size
                    // for i in cartlist_full.carts.size

                    val addressList: MutableList<Addresse> = mutableListOf()

                    for (i in 0 until addressSize) {
                        //val cart_id = addresslist_full.addresses[i].id
                        //Log.i("KKCC", "ID: $cart_id")

                        val _id_customer = addresslist_full.addresses[i].id_customer


                        if (getSharedPreferences(
                                loginSharedPrefName,
                                Context.MODE_PRIVATE
                            ).getInt("id", 0) == _id_customer.toInt()
                        ) {
                            Log.i("KKKKK", "OOOOOOOOHUUUUU matched1!!!!!!!!!!!!!!!!!!")

                            val address = addresslist_full.addresses[i]
                            Log.i("KKKKK", "Address: $address")

                            addressList += address

                        }
                    }
                    /*
                    Now time to show address list into recycler view
                     */
                    if (addressList.size > 0) {
                        address_list_empty_list.visibility = View.VISIBLE
                        initRecyclerView()
                        addDataSet(addressList)
                        address_list_empty_list.visibility = View.GONE
                    } else {
                        address_list_empty_list.visibility = View.VISIBLE
                        address_recycler_view.visibility = View.GONE
                    }

                    //Hide Progress Bar
                    if (address_progressBar.isVisible)
                        address_progressBar.visibility = View.GONE

                }
            }
        })
    }

    private fun addDataSet(addressList: MutableList<Addresse>) {
        addressAdapter.submitList(addressList, this)
    }

    private fun initRecyclerView() {

        address_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MyAddressActivity)
            addressAdapter = AddressRecyclerAdapter()
            adapter = addressAdapter

            addressAdapter.setAddressDeleteListener(object :
                AddressRecyclerAdapter.AddressDeleteListener {
                override fun onAddressDeleted() {
                    //Toast.makeText(this@MyAddressActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Address deleted successfully!", Toast.LENGTH_SHORT)
                        .show()

                    this@MyAddressActivity.finish()
                    startActivity(intent)

                }

                override fun onAddressEditClicked() {
                    Toast.makeText(this@MyAddressActivity, "Edit Clicked!!!", Toast.LENGTH_SHORT)
                        .show()

                }

            })
        }
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
