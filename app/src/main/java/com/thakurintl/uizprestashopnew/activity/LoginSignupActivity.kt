package com.thakurintl.uizprestashopnew.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.customclasses.HashType
import com.thakurintl.uizprestashopnew.customclasses.Md5Hasher
import com.thakurintl.uizprestashopnew.dataclass.CustomerResponse
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import kotlinx.android.synthetic.main.activity_login_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class LoginSignupActivity : AppCompatActivity() {

    //To save client details in sharedPreference
    private val loginSharedPrefName: String = "clientDetailsPref"

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        //Toolbar setup
        setSupportActionBar(login_signup_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Handling click event of the login button
        btn_login.setOnClickListener {

            if (edt_txt_email.text.trim().isNotEmpty() && edt_txt_password.text.trim().isNotEmpty()) {

                if (EMAIL_ADDRESS_PATTERN.matcher(edt_txt_email.text.trim()).matches()) {

                    Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show()
                    login(
                        edt_txt_email.text.trim().toString(),
                        edt_txt_password.text.trim().toString()
                    )

                } else {
                    Toast.makeText(this, "Enter Correct Email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill Credentials Before Logging in", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun login(email: String, pass: String) {

        Log.i("KKKKKK", "Email: $email  And hashed password: $pass")
        val md5Pass = Md5Hasher.hash(UtilityObjects.SERVER_COOKIE_KEY + pass, HashType.MD5)


        Log.i("KKKKKK", "Email: $email  And hashed password: $md5Pass")

        val services = ApiClient(this).getClient()?.create(ApiServices::class.java)


        val call: Call<CustomerResponse> =
            services!!.getCustomerDetails(
                "full",
                "[$email]",
                "[$md5Pass]", "JSON", UtilityObjects.API_KEY
            )


        call.enqueue(object : Callback<CustomerResponse> {
            override fun onFailure(call: Call<CustomerResponse>?, t: Throwable?) {
                if (t != null) {
                    Log.i("KKKKKK", "Throwable:  $t")
                    Toast.makeText(
                        this@LoginSignupActivity,
                        "Invalid Credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(
                call: Call<CustomerResponse>?,
                response: Response<CustomerResponse>?
            ) {
                if (response != null) {
                    val customer_details_full = response.body()
                    if (customer_details_full != null) {

                        val customer = customer_details_full.customers[0]

                        Log.i("KKKKKK", "Response Data:  $customer")
                        Log.i("KKKKKK", "Response:  $response")

                        Toast.makeText(
                            this@LoginSignupActivity,
                            "Welcome ${customer.firstname}",
                            Toast.LENGTH_SHORT
                        ).show()


                        MySharedPreferences.putData(
                            applicationContext,
                            true,
                            customer_details_full.customers[0].id,
                            customer_details_full.customers[0].firstname,
                            customer_details_full.customers[0].lastname,
                            customer_details_full.customers[0].email,
                            customer_details_full.customers[0].passwd,
                            customer_details_full.customers[0].id_gender
                        )


                        finish()

                    }
                }
            }

        })
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

