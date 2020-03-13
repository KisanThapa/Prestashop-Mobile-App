package com.thakurintl.uizprestashopnew.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thakurintl.uizprestashopnew.R
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        //Toolbar
        setSupportActionBar(cartToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }
}
