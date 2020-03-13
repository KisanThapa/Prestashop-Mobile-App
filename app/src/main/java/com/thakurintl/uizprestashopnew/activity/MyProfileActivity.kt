package com.thakurintl.uizprestashopnew.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)


        //Toolbar setup
        setSupportActionBar(my_profile_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        //personal info click
        person_info_layout.setOnClickListener {

            startActivity(Intent(this, MyPersonalInfoActivity::class.java))
        }

        //My Address Click
        my_address_layout.setOnClickListener {
            startActivity(Intent(this, MyAddressActivity::class.java))

        }

        //Orders History
        orders_history_layout.setOnClickListener {

            startActivity(Intent(this, OrdersHistoryActivity::class.java))

        }

        //edit password click
        edit_password_layout.setOnClickListener {

            startActivity(Intent(this, EditPasswordActivity::class.java))

        }


        //logout click
        logout_layout.setOnClickListener {


            MySharedPreferences.clear(applicationContext)


            finish()
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
