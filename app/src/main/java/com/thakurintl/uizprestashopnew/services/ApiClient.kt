package com.thakurintl.uizprestashopnew.services

import android.content.Context
import com.thakurintl.uizprestashopnew.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(val context: Context) {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {

        val url = context.getString(R.string.BASE_URL)

        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}
