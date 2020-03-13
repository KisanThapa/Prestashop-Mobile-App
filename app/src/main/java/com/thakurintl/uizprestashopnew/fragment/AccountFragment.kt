package com.thakurintl.uizprestashopnew.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.activity.*
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment() {


    //To save client details in sharedPreference
    private val loginSharedPrefName: String = "clientDetailsPref"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //This lets know that fragment also has menu and inflate menu
        setHasOptionsMenu(true)


        val view = inflater.inflate(R.layout.fragment_account, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_signup_layout.setOnClickListener {
            startActivity(Intent(context, LoginSignupActivity::class.java))
        }

        my_profile_layout.setOnClickListener {
            startActivity(Intent(context, MyProfileActivity::class.java))
        }

        //personal info click
        person_info_layout.setOnClickListener {

            startActivity(Intent(context, MyPersonalInfoActivity::class.java))
        }

        //My Address Click
        my_address_layout.setOnClickListener {
            startActivity(Intent(context, MyAddressActivity::class.java))
        }

        //Orders History
        orders_history_layout.setOnClickListener {

            startActivity(Intent(context, OrdersHistoryActivity::class.java))
        }
        //edit password click
        edit_password_layout.setOnClickListener {
            startActivity(Intent(context, EditPasswordActivity::class.java))
        }


        //logout click
        logout_layout.setOnClickListener {
            MySharedPreferences.clear(context!!)
            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false)
            }
            ft.detach(this).attach(this).commit()
        }


        if (MySharedPreferences.getLoginStatus(context!!.applicationContext)) {

            login_signup_layout.visibility = View.GONE
            my_profile_layout.visibility = View.VISIBLE
            //total_layout.visibility = View.VISIBLE
        } else {
            my_profile_layout.visibility = View.GONE
            //total_layout.visibility = View.GONE
            login_signup_layout.visibility = View.VISIBLE
        }

    }

    override fun onResume() {
        super.onResume()

        if (MySharedPreferences.getLoginStatus(context!!.applicationContext)) {

            login_signup_layout.visibility = View.GONE
            my_profile_layout.visibility = View.VISIBLE
            //total_layout.visibility = View.VISIBLE
        } else {
            my_profile_layout.visibility = View.GONE
            //total_layout.visibility = View.GONE
            login_signup_layout.visibility = View.VISIBLE
        }

    }
}
