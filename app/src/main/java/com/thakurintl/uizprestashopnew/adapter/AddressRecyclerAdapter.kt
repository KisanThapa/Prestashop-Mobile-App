package com.thakurintl.uizprestashopnew.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.dataclass.Addresse
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import com.thakurintl.uizprestashopnew.services.ApiClient
import com.thakurintl.uizprestashopnew.services.ApiServices
import kotlinx.android.synthetic.main.layout_address_list_item_view.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddressRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface AddressDeleteListener {
        fun onAddressDeleted()

        fun onAddressEditClicked()
    }

    var listener: AddressDeleteListener? = null
    fun setAddressDeleteListener(listener: AddressDeleteListener) {
        this.listener = listener
    }

    private var items: List<Addresse> = ArrayList()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AddressViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_address_list_item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddressViewHolder -> {
                holder.bind(items[position], context, listener!!)
            }
        }

    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun submitList(addressList: List<Addresse>, context: Context) {
        items = addressList

        this.context = context
    }


    class AddressViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val txtAddressAlias = itemView.txt_address_alias!!
        private val txtAddressFullName = itemView.txt_address_full_name
        private val txtAddressCompanyName = itemView.txt_address_company_name
        private val txtAddressOne = itemView.txt_address_one
        private val txtAddressTwo = itemView.txt_address_two
        private val txtAddressComplete = itemView.txt_address_complete
        private val txtAddressCountry = itemView.txt_address_country
        private val txtAddressPhone = itemView.txt_address_phone
        private val txtAddressPhoneMobile = itemView.txt_address_phone_mobile
        private val btnEdit = itemView.address_btn_edit
        private val btnDelete = itemView.address_btn_delete


        @SuppressLint("SetTextI18n")
        fun bind(address: Addresse, context: Context, listener: AddressDeleteListener) {

            txtAddressAlias.text = address.alias
            txtAddressFullName.text = address.firstname + " " + address.lastname
            txtAddressCompanyName.text = address.company
            txtAddressOne.text = address.address1
            txtAddressTwo.text = address.address2
            txtAddressComplete.text =
                address.city + ", " + address.id_state + " " + address.postcode
            txtAddressCountry.text = address.id_country
            txtAddressPhone.text = "Phone: " + address.phone
            txtAddressPhoneMobile.text = "Mobile: " + address.phone_mobile


            btnEdit.setOnClickListener {
                Log.i("****", "Message Edit: ${address.city}")

                //Edit Button clicked
                listener.onAddressEditClicked()

            }

            btnDelete.setOnClickListener {
                Log.i("****", "Message Delete: ${address.address1}")

                val services = ApiClient(context).getClient()?.create(ApiServices::class.java)
                val call =
                    services!!.deleteAddress(address.id, "full", "JSON", UtilityObjects.API_KEY)

                call.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.i("****", "Throwable: $t")
                        Toast.makeText(context, "Failed to delete address!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {

                        if (response.isSuccessful) {

                            //Delete Succeeded its time to refresh the activity
                            listener.onAddressDeleted()
                        }
                    }

                })

            }

        }

    }

}


//package com.thakurintl.uizprestashopnew.adapter
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.thakurintl.uizprestashopnew.R
//import com.thakurintl.uizprestashopnew.dataclass.Addresse
//import kotlinx.android.synthetic.main.layout_address_list_item_view.view.*
//
//
//class AddressRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//
//    private var items: List<Addresse> = ArrayList()
//    lateinit var context: Context
//
//    lateinit var onClickListener: ButtonClickAdapterListener
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//        return AddressViewHolder(
//
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.layout_address_list_item_view,
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is AddressViewHolder -> {
//                holder.bind(items[position])
//            }
//        }
//
//
//
//    }
//
//    override fun getItemCount(): Int {
//
//        return items.size
//    }
//
//    fun submitList(addressList: List<Addresse>, context: Context) {
//        items = addressList
//
//        this.context = context
//
//    }
//
//    class AddressViewHolder constructor(
//        itemView: View
//    ) : RecyclerView.ViewHolder(itemView) {
//
//        private val txtAddressAlias = itemView.txt_address_alias!!
//        private val txtAddressFullName = itemView.txt_address_full_name
//        private val txtAddressCompanyName = itemView.txt_address_company_name
//        private val txtAddressOne = itemView.txt_address_one
//        private val txtAddressTwo = itemView.txt_address_two
//        private val txtAddressComplete = itemView.txt_address_complete
//        private val txtAddressCountry = itemView.txt_address_country
//        private val txtAddressPhone = itemView.txt_address_phone
//        private val txtAddressPhoneMobile = itemView.txt_address_phone_mobile
//        val btnEdit = itemView.address_btn_edit
//        val btnDelete = itemView.address_btn_delete
//
//
//        @SuppressLint("SetTextI18n")
//        fun bind(address: Addresse) {
//
//            txtAddressAlias.text = address.alias
//            txtAddressFullName.text = address.firstname + " " + address.lastname
//            txtAddressCompanyName.text = address.company
//            txtAddressOne.text = address.address1
//            txtAddressTwo.text = address.address2
//            txtAddressComplete.text =
//                address.city + ", " + address.id_state + " " + address.postcode
//            txtAddressCountry.text = address.id_country
//            txtAddressPhone.text = "Phone: " + address.phone
//            txtAddressPhoneMobile.text = "Mobile: " + address.phone_mobile
//
//
//        }
//
//    }
//
//    //region Interface Details listener
//    interface ButtonClickAdapterListener {
//        fun EditOnClick(v: View?, position: Int)
//        fun DeleteOnClick(v: View?, position: Int)
//    }
//
//}