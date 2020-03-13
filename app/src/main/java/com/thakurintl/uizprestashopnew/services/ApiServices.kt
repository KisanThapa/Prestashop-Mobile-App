package com.thakurintl.uizprestashopnew.services

import com.thakurintl.uizprestashopnew.dataclass.*
import com.thakurintl.uizprestashopnew.modelclass.CombinationListModel
import com.thakurintl.uizprestashopnew.modelclass.OrderStatusModel
import com.thakurintl.uizprestashopnew.modelclass.ProductOptionModel
import com.thakurintl.uizprestashopnew.modelclass.ProductOptionValueDetailModel
import com.thakurintl.uizprestashopnew.viewmodels.OrdersModelClass
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiServices {

    @GET("customers")
    fun getCustomerDetails(
        @Query("display") display: String,
        @Query("filter[email]") email: String,
        @Query("filter[passwd])") pass: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<CustomerResponse>


    @GET("carts")
    fun getCartList(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<CartListResponseModel>


    @GET("addresses")
    fun getAddressList(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<AddressListResponseModel>


    @GET("products")
    fun getProductList(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<ProductListModel>


    @GET("stock_availables")
    fun getStockAvailableDetails(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<StocksAvailableListModel>


    @DELETE("addresses/{id}/")
    fun deleteAddress(
        @Path("id") id: Int,
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<Void>


    @DELETE("carts/{id}/")
    fun deleteCarts(
        @Path("id") id: Int,
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<Void>

    @GET("products")
    fun getAllProductList(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<ProductListModel>

    @GET("products")
    fun searchItem(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String,
        @Query("limit") limit: String,
        @Query("filter[name]") filterName: String
    ): Call<ProductListModel>

//    @GET("products/{id}/")
//    fun getSingleProductDetail(
//        @Path("id") id: Int,
//        @Query("output_format") json: String,
//        @Query("ws_key") key: String
//    ): Call<SingleProductModel>


//    @GET("combinations/{id}/")
//    fun getCombinationDetailbyId(
//        @Path("id") id: Int,
//        @Query("output_format") json: String,
//        @Query("ws_key") key: String
//    ): Call<CombinationDetailModel>


    @GET("combinations/")
    fun getAllCombinations(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<CombinationListModel>


    @GET("product_option_values/{id}")
    fun getProductOptionValuesbyId(
        @Path("id") id: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<ProductOptionModel>


    @GET("product_option_values/")
    fun getProductOptionValues(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<ProductOptionValueDetailModel>


    @GET("orders/")
    fun getOrderList(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<OrdersModelClass>

    @GET("order_states/")
    fun getOrderStatus(
        @Query("display") display: String,
        @Query("output_format") json: String,
        @Query("ws_key") key: String
    ): Call<OrderStatusModel>


    @POST("carts/")
    fun postCartDetails(
        @Header("Content-Type") contentType: String?,
        @Body body: RequestBody,
        @Query("output_format") json: String,
        @Query("ws_key") key: String


    ): Call<Cart>
}

