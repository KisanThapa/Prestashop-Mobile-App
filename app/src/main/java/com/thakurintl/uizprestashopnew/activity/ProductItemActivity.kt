package com.thakurintl.uizprestashopnew.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.paypal.android.sdk.payments.*
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.dataclass.Cart
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.modelclass.AttributeModel
import com.thakurintl.uizprestashopnew.modelclass.Combination
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import com.thakurintl.uizprestashopnew.objects.ProductDetailsObject
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_product_item.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductItemActivity : AppCompatActivity() {

    var productId: Int? = null
    var productName: String? = null

    var attrDataWithId: List<List<AttributeModel>>? = null

    var selectedSpinner: MutableList<AttributeModel>? = null

    var prodCheckOutPrice: Int = 0
    private var prodCheckOutName = ""
    private var prodCheckOutShift = ""
    private var prodCheckOutSkill = ""
    private var prodCheckOutTime = ""
    private var prodCheckOutLanguage = ""


    companion object {
        private const val CLIENT_ID: String =
            "AQzZo7HHeQNUOyDHODGkAAxpGIh3-2fjE7b5w9DcjBVMDejG5xZKKDgmBhJFLtg43EwCas7MxSvBZH6Q"

        private const val PAYPAL_REQUEST_CODE = 111
        //Change environment also
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_item)

        productId = intent!!.getIntExtra("PID", 0)
        productName = intent!!.getStringExtra("PNAME")


        //Initialization of the selected spinner
        selectedSpinner = mutableListOf(
            AttributeModel(0, ""),
            AttributeModel(0, ""),
            AttributeModel(0, ""),
            AttributeModel(0, "")
        )

        //Toolbar setup
        setSupportActionBar(single_item_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = productName


        filterSingleProduct()


        var wrkSpnCount = 0
        var sklSpnCount = 0
        var durSpnCount = 0
        var lngSpnCount = 0

        //var selectedCombId = 0

        spnr_working_shift?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {
                    selectedSpinner!![0] = listOfAttrWithId1[position]

                    if (wrkSpnCount > 0) {
                        val selectedSpnrIds = mutableListOf(0, 0, 0, 0)

                        for (j in 0 until selectedSpinner!!.size) {
                            selectedSpnrIds[j] = (selectedSpinner!![j].id)
                        }

                        val filteredSpnrIds = mutableListOf(0, 0, 0, 0)
                        for (i in 0 until thisCombination.size) {
                            val list = thisCombination[i].associations.product_option_values

                            for (k in list.indices) {
                                filteredSpnrIds[k] = list[k].id.toInt()
                            }
                            if (selectedSpnrIds.containsAll(filteredSpnrIds)) {
                                //selectedCombId = thisCombination[i].id

                                txt_prod_price.text = "$" + UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(),
                                    2
                                )

                                //This is amount to pay
                                prodCheckOutPrice = UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(), 2
                                ).toInt()

                                if (!btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorPrimary,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = true
                                }
                                /**
                                 * Required combination is found
                                 * so its time to break the loop
                                 */
                                break
                            } else {
                                //Error alert for wrong combination
//                                Toasty.error(
//                                    this@ProductItemActivity,
//                                    "Combination Not Found!",
//                                    Toast.LENGTH_SHORT, true
//                                ).show()
                                //Toast.makeText(this@ProductItemActivity, "Combination Not Found!", Toast.LENGTH_SHORT).show()

                                txt_prod_price.text = "Combination Not Found!"

                                if (btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorLightGrey,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = false
                                }
                            }
                        }
                    }
                    wrkSpnCount++
                } catch (e: Exception) {
                    Log.i("XXXXX", e.toString())
                }
            }
        }

        spnr_skills?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {
                    selectedSpinner!![1] = listOfAttrWithId2[position]

                    if (sklSpnCount > 0) {
                        val selectedSpnrIds = mutableListOf(0, 0, 0, 0)
                        for (j in 0 until selectedSpinner!!.size) {
                            selectedSpnrIds[j] = (selectedSpinner!![j].id)
                        }

                        val filteredSpnrIds = mutableListOf(0, 0, 0, 0)
                        for (i in 0 until thisCombination.size) {

                            Log.i("RERE", "filteredCombList:  $thisCombination")

                            val list = thisCombination[i].associations.product_option_values

                            for (k in list.indices) {
                                filteredSpnrIds[k] = list[k].id.toInt()
                            }

                            if (selectedSpnrIds.containsAll(filteredSpnrIds)) {
                                //selectedCombId = thisCombination[i].id

                                txt_prod_price.text = "$" + UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(),
                                    2
                                )
                                //This is amount to pay
                                prodCheckOutPrice = UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(), 2
                                ).toInt()


                                if (!btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorPrimary,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = true
                                }
                                /**
                                 * Required combination is found
                                 * so its time to break the loop
                                 */
                                break
                            } else {
                                //Error alert for wrong combination
//                                Toasty.error(
//                                    this@ProductItemActivity,
//                                    "Combination Not Found!",
//                                    Toast.LENGTH_SHORT, true
//                                ).show()
//                                Toast.makeText(this@ProductItemActivity, "Combination Not Found!", Toast.LENGTH_SHORT).show()


                                txt_prod_price.text = "Combination Not Found!"
                                if (btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorLightGrey,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = false

                                }
                            }
                        }

                    }
                    sklSpnCount++
                } catch (e: Exception) {
                    Log.i("XXXXX", e.toString())
                }

            }

        }

        spnr_duration?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {
                    selectedSpinner!![2] = listOfAttrWithId3[position]

                    if (durSpnCount > 0) {
                        val selectedSpnrIds = mutableListOf(0, 0, 0, 0)

                        for (j in 0 until selectedSpinner!!.size) {
                            selectedSpnrIds[j] = (selectedSpinner!![j].id)
                        }

                        val filteredSpnrIds = mutableListOf(0, 0, 0, 0)
                        for (i in 0 until thisCombination.size) {

                            Log.i("RERE", "filteredCombList:  $thisCombination")

                            val list = thisCombination[i].associations.product_option_values

                            for (k in list.indices) {
                                filteredSpnrIds[k] = list[k].id.toInt()
                            }
                            if (selectedSpnrIds.containsAll(filteredSpnrIds)) {
//                                selectedCombId = thisCombination[i].id

                                txt_prod_price.text = "$" + UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(),
                                    2
                                )
                                //This is amount to pay
                                prodCheckOutPrice = UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(), 2
                                ).toInt()


                                if (!btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorPrimary,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = true
                                }
                                /**
                                 * Required combination is found
                                 * so its time to break the loop
                                 */
                                break
                            } else {
                                //Error alert for wrong combination
//                                Toasty.error(
//                                    this@ProductItemActivity,
//                                    "Combination Not Found!",
//                                    Toast.LENGTH_SHORT, true
//                                ).show()
//                                Toast.makeText(this@ProductItemActivity, "Combination Not Found!", Toast.LENGTH_SHORT).show()

                                txt_prod_price.text = "Combination Not Found!"
                                if (btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorLightGrey,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = false
                                }

                            }

                        }

                    }
                    durSpnCount++
                } catch (e: Exception) {
                    Log.i("XXXXX", e.toString())
                }

            }

        }

        spnr_language?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {
                    selectedSpinner!![3] = listOfAttrWithId4[position]

                    if (lngSpnCount > 0) {
                        val selectedSpnrIds = mutableListOf(0, 0, 0, 0)

                        for (j in 0 until selectedSpinner!!.size) {
                            selectedSpnrIds[j] = (selectedSpinner!![j].id)
                        }

                        val filteredSpnrIds = mutableListOf(0, 0, 0, 0)
                        for (i in 0 until thisCombination.size) {

                            Log.i("RERE", "filteredCombList:  $thisCombination")

                            val list = thisCombination[i].associations.product_option_values

                            for (k in list.indices) {
                                filteredSpnrIds[k] = list[k].id.toInt()
                            }
                            if (selectedSpnrIds.containsAll(filteredSpnrIds)) {
                                //selectedCombId = thisCombination[i].id

                                txt_prod_price.text = "$" + UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(),
                                    2
                                )
                                //This is amount to pay
                                prodCheckOutPrice = UtilityObjects.truncateDecimal(
                                    thisCombination[i].price.toDouble(), 2
                                ).toInt()



                                if (!btn_check_out.isEnabled) {

                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorPrimary,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = true

                                }
                                /**
                                 * Required combination is found
                                 * so its time to break the loop
                                 */

                                break
                            } else {
                                //Error alert for wrong combination
//                                Toasty.error(
//                                    this@ProductItemActivity,
//                                    "Combination Not Found!",
//                                    Toast.LENGTH_SHORT, true
//                                ).show()
//                                Toast.makeText(this@ProductItemActivity, "Combination Not Found!", Toast.LENGTH_SHORT).show()

                                txt_prod_price.text = "Combination Not Found!"
                                if (btn_check_out.isEnabled) {
                                    btn_check_out.setBackgroundColor(
                                        ResourcesCompat.getColor(
                                            resources,
                                            R.color.colorLightGrey,
                                            null
                                        )
                                    )
                                    btn_check_out.isEnabled = false
                                }

                            }

                        }

                    }
                    lngSpnCount++
                } catch (e: Exception) {
                    Log.i("XXXXX", e.toString())
                }

            }

        }

        thisProductCombination()

        //Add to carts
        btn_add_to_cart.setOnClickListener {
            addToCart()
        }

        /**
         * Paypal stuffs
         */
        btn_check_out.setOnClickListener {
            getPayment()
        }
        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration)
        startService(intent)

    }

    private fun filterSingleProduct() {

        lateinit var singleProductModel: Product

        for (item in UtilityObjects.savedProducts!!.products) {
            if (item.id == productId) {
                singleProductModel = item

                break
            }
        }
        displaySingleDatainView(singleProductModel)
        filterDefCombination(singleProductModel.id_default_combination)

    }

    private fun addToCart() {

        val cartXML = getCartXML()
        val cartBody: RequestBody = RequestBody.create(MediaType.parse("application/xml"), cartXML)

        Log.i("QWER", "RequestBody:  ${cartBody.contentType()}")
        Log.i("QWER", "RequestBody:  ${cartBody.contentLength()}")


        val services = ApiClient(this).getClient()!!.create(ApiServices::class.java)

        val call =
            services.postCartDetails("application/xml", cartBody, "JSON", UtilityObjects.API_KEY)

        call.enqueue(object : Callback<Cart> {
            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Log.i("QWER", "onFailure:  $t")
            }

            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {

                if (response.isSuccessful) {

                    Log.i("QWER", "response Successful:  $response")

                } else {
                    Log.i("QWER", "response:  ${response.headers()}")
                    Log.i("QWER", "response:  ${response.message()}")
                    Log.i("QWER", "response:  ${response.errorBody()}")
                    Log.i("QWER", "response:  ${response.body()}")
                }
            }
        })
    }

    private fun getCartXML(): String {
        val data = //"<?xml version=\"1.0\"encoding=\"UTF-8\"?>\n" +
            "<prestashop xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                    "<cart>\n" +
                    "<id></id>\n" +
                    "<id_address_delivery>2</id_address_delivery>\n" +
                    "<id_address_invoice>2</id_address_invoice>\n" +
                    "<id_currency>" + "1" + "</id_currency>\n" +
                    "<id_customer>" + MySharedPreferences.getId(this).toString() + "</id_customer>\n" +
                    "<id_guest>0</id_guest>\n" +
                    "<id_lang>" + "1" + "</id_lang>\n" +
                    "<id_shop_group>" + "1" + "</id_shop_group>\n" +
                    "<id_shop>" + "1" + "</id_shop>\n" +
                    "<id_carrier>" + "2" + "</id_carrier>\n" +
                    "<recyclable>1</recyclable>\n" +
                    "<gift>0</gift>\n" +
                    "<gift_message></gift_message>\n" +
                    "<mobile_theme>0</mobile_theme >\n" +
                    "<delivery_option></delivery_option>\n" +
                    "<secure_key></secure_key>\n" +
                    "<allow_seperated_package>0</allow_seperated_package>\n" +
                    "<date_add></date_add>\n" +
                    "<date_upd></date_upd>\n" +
                    "<associations>\n" +
                    "<cart_rows>\n" +
                    "<cart_row>\n" +
                    "<id_product>" + productId.toString() + "</id_product>\n" +
                    "<id_product_attribute></id_product_attribute>\n" +
                    "<id_address_delivery></id_address_delivery>\n" +
                    "<quantity>1</quantity>\n" +
                    "</cart_row>\n" +
                    "</cart_rows>\n" +
                    "</associations>\n" +
                    "</cart>\n" +
                    "</prestashop>"

        return data
    }

    private var payPalConfiguration: PayPalConfiguration = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
        .clientId(CLIENT_ID)

    private fun getPayment() {
        //Creating a paypalpayment
        val payment = PayPalPayment(
            BigDecimal(prodCheckOutPrice),
            "USD",
            prodCheckOutName,
            PayPalPayment.PAYMENT_INTENT_SALE
        )
        //Creating Paypal Payment activity intent
        val intent = Intent(this, PaymentActivity::class.java)

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

        startActivityForResult(intent, 0)
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                val confirm: PaymentConfirmation =
                    data!!.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                try {

                    val paymentDetails = confirm.toJSONObject().toString(4)
                    Log.i("PPPPAAAAYYYY", "paymentDetails:  $paymentDetails")

                    Log.i("paymentExample", confirm.toJSONObject().toString(4))
                    Toasty.success(this, "Payment Successful.....", Toast.LENGTH_LONG, true).show()

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details
                    this.finish()

                } catch (e: JSONException) {
                    Log.e(
                        "paymentExample",
                        "an extremely unlikely failure occurred: ",
                        e
                    )
                    Toasty.error(this, "Exception: $e", Toasty.LENGTH_LONG, true).show()
                }
            }
            Activity.RESULT_CANCELED -> {
                Log.i("paymentExample", "The user canceled.")
                Toasty.error(this, "The user canceled.", Toasty.LENGTH_SHORT, true).show()
            }
            PaymentActivity.RESULT_EXTRAS_INVALID -> {
                Log.i(
                    "paymentExample",
                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs."
                )
                Toasty.error(
                    this,
                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.",
                    Toasty.LENGTH_SHORT,
                    true
                ).show()

            }

        }

    }


    lateinit var thisCombination: MutableList<Combination>
    private fun thisProductCombination() {

        val temp: MutableList<Combination> =
            mutableListOf()
        for (i in 0 until UtilityObjects.savedCombination!!.combinations.size) {
            if (UtilityObjects.savedCombination!!.combinations[i].id_product.toInt() == productId) {
                temp.add(UtilityObjects.savedCombination!!.combinations[i])
            }
        }
        thisCombination = temp

    }

    @SuppressLint("SetTextI18n")
    private fun filterDefCombination(idDefaultCombination: Int) {

        lateinit var combination: Combination

        for (item in UtilityObjects.savedCombination!!.combinations) {
            if (item.id == idDefaultCombination) {
                combination = item
                break
            }
        }

        txt_prod_price.text = "$" +
                UtilityObjects.truncateDecimal(
                    combination.price.toDouble(),
                    2
                )
        // its time set default price into checkout price
        prodCheckOutPrice =
            UtilityObjects.truncateDecimal(
                combination.price.toDouble(),
                2
            ).toInt()

    }


    @SuppressLint("SetTextI18n")
    private fun displaySingleDatainView(singleProductModel: Product) {

        val imageIds = mutableListOf<String>()

        Log.i("UUUUUU", "Total images of the product:  $imageIds")

        txt_prod_name.text = singleProductModel.name
        //This is shown in paypal checkout
        prodCheckOutName = singleProductModel.name

        txt_prod_price.text =
            "$" + UtilityObjects.truncateDecimal(singleProductModel.price.toDouble(), 2).toString()


        val textDetails = singleProductModel.description_short.replaceFirst("<p><span>", "")
            .replaceFirst("</p></span>", "")

        txt_prod_details.text = textDetails

        val singleProductDetails = ProductDetailsObject()

//        singleProductDetails.getAllProductOptions(this)

        singleProductDetails.getProductOptionValues(this)

        singleProductDetails.setAttributeSuccessListener(object :
            ProductDetailsObject.AttributeSuccessCallback {
            override fun onSuccess(attrData: MutableList<MutableList<AttributeModel>>) {

                attrDataWithId = attrData

                setSpinner(attrData.distinct())

            }

        })

    }

    private val listOfAttrWithId1 = mutableListOf<AttributeModel>()
    private val listOfAttrWithId2 = mutableListOf<AttributeModel>()
    private val listOfAttrWithId3 = mutableListOf<AttributeModel>()
    private val listOfAttrWithId4 = mutableListOf<AttributeModel>()


    private val listOfAttr1 = mutableListOf<String>()
    private val listOfAttr2 = mutableListOf<String>()
    private val listOfAttr3 = mutableListOf<String>()
    private val listOfAttr4 = mutableListOf<String>()


    private fun setSpinner(attrLists: List<MutableList<AttributeModel>>) {

        Log.i("QQQQQQQ", "attrLists:  $attrLists")
        for (i in attrLists[0].indices) {
            listOfAttr1.add(attrLists[0][i].attrName)
            listOfAttrWithId1.add(i, attrLists[0][i])
        }
        Log.i("WWWWWWWW", "Data1: $listOfAttrWithId1")
        val spinnerArrayAdapterWorkingShift: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOfAttr1
        ) //selected item will look like a spinner set from XML
        spinnerArrayAdapterWorkingShift.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnr_working_shift.adapter = spinnerArrayAdapterWorkingShift



        for (i in attrLists[1].indices) {
            listOfAttr2.add(attrLists[1][i].attrName)
            listOfAttrWithId2.add(i, attrLists[1][i])
        }
        Log.i("WWWWWWWW", "Data1: $listOfAttr2")
        val spinnerArrayAdapterSkills: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOfAttr2
        ) //selected item will look like a spinner set from XML
        spinnerArrayAdapterSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnr_skills.adapter = spinnerArrayAdapterSkills



        for (i in attrLists[2].indices) {
            listOfAttrWithId3.add(i, attrLists[2][i])
            listOfAttr3.add(attrLists[2][i].attrName)
        }
        Log.i("WWWWWWWW", "Data1: $listOfAttr3")
        val spinnerArrayAdapterDuration: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOfAttr3
        ) //selected item will look like a spinner set from XML
        spinnerArrayAdapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnr_duration.adapter = spinnerArrayAdapterDuration



        for (i in attrLists[3].indices) {
            listOfAttrWithId4.add(i, attrLists[3][i])
            listOfAttr4.add(attrLists[3][i].attrName)
        }
        Log.i("WWWWWWWW", "Data1: $listOfAttr4")
        val spinnerArrayAdapterLanguage: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOfAttr4
        ) //selected item will look like a spinner set from XML
        spinnerArrayAdapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnr_language.adapter = spinnerArrayAdapterLanguage

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

