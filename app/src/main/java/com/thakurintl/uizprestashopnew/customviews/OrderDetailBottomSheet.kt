package com.thakurintl.uizprestashopnew.customviews

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import com.thakurintl.uizprestashopnew.viewmodels.Order
import kotlinx.android.synthetic.main.order_bottomsheet_layout.view.*

class OrderDetailBottomSheet(private val singleOrderDetail: Order) : BottomSheetDialogFragment() {

    fun newInstance(): OrderDetailBottomSheet {
        return OrderDetailBottomSheet(singleOrderDetail)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.order_bottomsheet_layout, container, false)

        view.btnOrderDetailClose.setOnClickListener {
            this.dismiss()
        }

        view.bottom_sheet_order_name.text = UtilityObjects.truncateProductName(
            singleOrderDetail.associations.order_rows[0].product_name
        )

        view.bottom_sheet_order_price.text = "$" +
                UtilityObjects.truncateDecimal(singleOrderDetail!!.total_paid.toDouble(), 2)

        view.bottom_sheet_order_status_layout.setCardBackgroundColor(
            Color.parseColor(
                singleOrderDetail.order_status.color
            )
        )
        view.bottom_sheet_order_status.text = singleOrderDetail.order_status.name
        view.bottom_sheet_order_date.text = singleOrderDetail.date_add.take(10)

        view.order_ref_code.text = singleOrderDetail.reference

        view.order_prod_name.text = singleOrderDetail.associations.order_rows[0].product_name

        view.order_quantity.text = singleOrderDetail.associations.order_rows[0].product_quantity

        view.order_unit_price.text = singleOrderDetail.associations.order_rows[0].product_price

        view.order_items_tax_incl.text =
            singleOrderDetail.associations.order_rows[0].unit_price_tax_incl
        view.order_items_tax_excl.text =
            singleOrderDetail.associations.order_rows[0].unit_price_tax_excl

        view.order_shipping_tax_incl.text = singleOrderDetail.total_shipping_tax_incl

        view.order_total_amount.text = singleOrderDetail.total_paid


        Log.i("uuuuuuu", "ADD Delivery: ${singleOrderDetail.address_delivery}")
        Log.i("uuuuuuu", "ADD Delivery: ${singleOrderDetail.address_invoice}")


        return view
    }

}