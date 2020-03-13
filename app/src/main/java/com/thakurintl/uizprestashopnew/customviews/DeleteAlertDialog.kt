package com.thakurintl.uizprestashopnew.customviews

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.thakurintl.uizprestashopnew.R

class DeleteAlertDialog(val context: Context) {


    var listener: AlertDialogListener? = null

    @SuppressLint("InflateParams")
    fun showDialog(msg: String) {

        val alertDialogBuilder = AlertDialog.Builder(context)
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater.inflate(R.layout.cart_alert_dialog_layout, null)

        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false)
        val dialog = alertDialogBuilder.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()


        view.findViewById<TextView>(R.id.txt_cart_alert).text = msg

        view.findViewById<Button>(R.id.cart_alert_btn_edit).setOnClickListener {

            //Toast.makeText(context, "EDiTTTTT", Toast.LENGTH_SHORT).show()

            listener?.onEditButtonClicked()

            dialog.dismiss()

        }

        view.findViewById<Button>(R.id.cart_alert_btn_delete).setOnClickListener {

            //Toast.makeText(context, "DElllelltle", Toast.LENGTH_SHORT).show()

            listener?.onDeleteButtonClicked()

//            val services = ApiClient(context).getClient()?.create(ApiServices::class.java)
//            val call =
//                services!!.deleteCarts(id, "full", "JSON", UtilityObjects.API_KEY)
//
//            call.enqueue(object : Callback<Void> {
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.i("****", "Throwable: $t")
//                    Toast.makeText(
//                        context,
//                        "Failed to remove item from your cart!",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//
//                    if (response.isSuccessful) {
//                        Toast.makeText(
//                            context,
//                            "Item removed from your cart",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//
//                    }
//                }
//
//            })

            dialog.dismiss()

        }


    }

    fun setAlertDialogListener(listener: AlertDialogListener) {
        this.listener = listener
    }

    interface AlertDialogListener {

        fun    onEditButtonClicked()

        fun onDeleteButtonClicked()
    }


}