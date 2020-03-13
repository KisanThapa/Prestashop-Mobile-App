package com.thakurintl.uizprestashopnew.objects

import android.content.Context
import com.thakurintl.uizprestashopnew.modelclass.CombinationListModel
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CombinationDetailsObject {
    var allCombinationCallback: AllCombinationCallback? = null

    interface AllCombinationCallback {
        fun onSuccess(combinationList: CombinationListModel)
    }

    fun setOnAllCombinationListener(allCombinationCallback: AllCombinationCallback) {
        this.allCombinationCallback = allCombinationCallback
    }

    fun getAllCombinations(context: Context) {
        val services = ApiClient(context).getClient()!!.create(ApiServices::class.java)

        val call =
            services.getAllCombinations("full", "JSON", UtilityObjects.API_KEY)

        call.enqueue(object : Callback<CombinationListModel> {
            override fun onFailure(call: Call<CombinationListModel>, t: Throwable) {}

            override fun onResponse(
                call: Call<CombinationListModel>,
                response: Response<CombinationListModel>
            ) {
                if (response.isSuccessful) {

                    val combinations = response.body()

                    //Saving data in utility objects
                    UtilityObjects.savedCombination = combinations!!

                    allCombinationCallback!!.onSuccess(combinations!!)

                }

            }

        })
    }


//    var allFilteredCombinationCallback: AllFilteredCombinationCallback? = null
//
//    interface AllFilteredCombinationCallback {
//        fun onSuccess(combinationList: MutableList<Combination>)
//    }
//
//    fun setOnFilteredCombinationListener(allFilteredCombinationCallback: AllFilteredCombinationCallback) {
//        this.allFilteredCombinationCallback = allFilteredCombinationCallback
//    }
//
//    fun getFilteredCombinations(context: Context, productId: Int) {
//
//        val services = ApiClient(context).getClient()!!.create(ApiServices::class.java)
//
//        val call =
//            services.getAllCombinations("full", "JSON", UtilityObjects.API_KEY)
//
//        call.enqueue(object : Callback<CombinationListModel> {
//            override fun onFailure(call: Call<CombinationListModel>, t: Throwable) {}
//
//            override fun onResponse(
//                call: Call<CombinationListModel>,
//                response: Response<CombinationListModel>
//            ) {
//                if (response.isSuccessful) {
//
//                    val combinations = response.body()
//
//                    val filteredCombination: MutableList<Combination> =
//                        mutableListOf()
//
//                    //Filter combination
//                    for (i in 0 until combinations!!.combinations.size) {
//
//                        if (combinations.combinations[i].id_product.toInt() == productId) {
//
//                            filteredCombination.add(combinations.combinations[i])
//
//                        }
//                    }
//                    allFilteredCombinationCallback!!.onSuccess(filteredCombination)
//
//                }
//
//            }
//
//        })
//
//    }

}