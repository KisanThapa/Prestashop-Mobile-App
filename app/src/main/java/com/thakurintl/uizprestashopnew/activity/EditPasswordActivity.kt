package com.thakurintl.uizprestashopnew.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thakurintl.uizprestashopnew.R
import kotlinx.android.synthetic.main.activity_edit_password.*

class EditPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        //Toolbar setup
        setSupportActionBar(edit_password_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)





    }
}
