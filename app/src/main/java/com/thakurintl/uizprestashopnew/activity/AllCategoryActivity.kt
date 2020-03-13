package com.thakurintl.uizprestashopnew.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thakurintl.uizprestashopnew.R
import kotlinx.android.synthetic.main.activity_all_category.*

class AllCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_category)


        //Toolbar
        setSupportActionBar(all_category_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
