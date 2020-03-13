package com.thakurintl.uizprestashopnew.objects

import android.content.Context
import android.util.Log
import com.thakurintl.uizprestashopnew.dataclass.ProductListModel
import com.thakurintl.uizprestashopnew.modelclass.AttributeModel
import com.thakurintl.uizprestashopnew.modelclass.ProductOptionValueDetailModel
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsObject {

    var attributeSuccessCallback: AttributeSuccessCallback? = null

    interface AttributeSuccessCallback {
        fun onSuccess(attrData: MutableList<MutableList<AttributeModel>>)
    }

    fun setAttributeSuccessListener(attributeSuccessCallback: AttributeSuccessCallback) {
        this.attributeSuccessCallback = attributeSuccessCallback
    }

    fun getProductOptionValues(context: Context) {

        val attrList1 = mutableListOf<MutableList<AttributeModel>>()

        val services = ApiClient(context).getClient()!!.create(ApiServices::class.java)
        val call = services.getProductOptionValues("full", "JSON", UtilityObjects.API_KEY)

        call.enqueue(object : Callback<ProductOptionValueDetailModel> {
            override fun onFailure(call: Call<ProductOptionValueDetailModel>, t: Throwable) {}

            override fun onResponse(
                call: Call<ProductOptionValueDetailModel>,
                response: Response<ProductOptionValueDetailModel>
            ) {

                if (response.isSuccessful) {

                    val valueDetailModel = response.body()

                    Log.i("FFFFFF", "DATA:    $valueDetailModel")

                    for (n in valueDetailModel!!.product_option_values.indices) {

                        val attrId = valueDetailModel.product_option_values[n].id_attribute_group
                        //val vName = valueDetailModel.product_option_values[n].name

                        val attrList = mutableListOf<AttributeModel>()

                        for (i in valueDetailModel.product_option_values.indices) {

                            if (attrId == valueDetailModel.product_option_values[i].id_attribute_group) {

//                                attrList.add(valueDetailModel.product_option_values[i].name)
                                attrList.add(
                                    AttributeModel(
                                        valueDetailModel.product_option_values[i].id,
                                        valueDetailModel.product_option_values[i].name
                                    )
                                )

                            }
                            if (!attrList.isNullOrEmpty())
                                attrList1.add(attrList)
                        }

                    }

                    Log.i("FFFFFFF", "Data Attr:  ${attrList1.distinct()}")

                    attributeSuccessCallback!!.onSuccess(attrList1)

                }

            }

        })

    }

    var allProductSuccessCallback: AllProductSuccessCallback? = null

    interface AllProductSuccessCallback {
        fun onSuccess(productListModel: ProductListModel)
    }

    fun setAllProductSuccessListener(allProductSuccessCallback: AllProductSuccessCallback) {
        this.allProductSuccessCallback = allProductSuccessCallback
    }

    fun getAllProductList(context: Context) {
        val services = ApiClient(context).getClient()?.create(ApiServices::class.java)
        val callProductList = services!!.getAllProductList("full", "JSON", UtilityObjects.API_KEY)

        callProductList.enqueue(object : Callback<ProductListModel> {
            override fun onFailure(call: Call<ProductListModel>, t: Throwable) {}

            override fun onResponse(
                call: Call<ProductListModel>,
                response: Response<ProductListModel>
            ) {
                if (response.isSuccessful) {

                    val productList = response.body()

                    //Saving data in utility objects
                    UtilityObjects.savedProducts = productList!!

                    allProductSuccessCallback!!.onSuccess(productList!!)
                }

            }

        })
    }

}


