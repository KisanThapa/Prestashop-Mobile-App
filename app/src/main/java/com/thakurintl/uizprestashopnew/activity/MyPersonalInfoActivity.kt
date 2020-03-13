package com.thakurintl.uizprestashopnew.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import kotlinx.android.synthetic.main.activity_my_personal_info.*

class MyPersonalInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_personal_info)

        //Toolbar setup
        setSupportActionBar(my_personal_info_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        personal_detail_firstname.setText(
            MySharedPreferences.getFirstName(this)!!,
            TextView.BufferType.EDITABLE
        )

        personal_detail_lastname.setText(
            MySharedPreferences.getLastName(this),
            TextView.BufferType.EDITABLE
        )
        personal_detail_email.setText(
            MySharedPreferences.getEmail(this),
            TextView.BufferType.EDITABLE
        )

        when (MySharedPreferences.getGender(this)) {
            1 -> {
                radio_mrs.isChecked = true
            }
            0 -> {
                radio_mr.isChecked = true
            }
            else -> {
            }
        }

    }
}
