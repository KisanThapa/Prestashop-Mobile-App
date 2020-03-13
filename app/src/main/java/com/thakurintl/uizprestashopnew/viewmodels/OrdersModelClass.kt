package com.thakurintl.uizprestashopnew.viewmodels

import com.thakurintl.uizprestashopnew.dataclass.Addresse
import com.thakurintl.uizprestashopnew.modelclass.OrderState

data class OrdersModelClass(
    val orders: List<Order>
)

data class Order(
    val associations: Associations,
    val carrier_tax_rate: String,
    val conversion_rate: String,
    val current_state: String,
    val date_add: String,
    val date_upd: String,
    val delivery_date: String,
    val delivery_number: String,
    val gift: String,
    val gift_message: String,
    val id: Int,
    val id_address_delivery: String,
    val id_address_invoice: String,
    val id_carrier: String,
    val id_cart: String,
    val id_currency: String,
    val id_customer: String,
    val id_lang: String,
    val id_shop: String,
    val id_shop_group: String,
    val invoice_date: String,
    val invoice_number: String,
    val mobile_theme: String,
    val module: String,
    val payment: String,
    val recyclable: String,
    val reference: String,
    val round_mode: String,
    val round_type: String,
    val secure_key: String,
    val shipping_number: String,
    val total_discounts: String,
    val total_discounts_tax_excl: String,
    val total_discounts_tax_incl: String,
    val total_paid: String,
    val total_paid_real: String,
    val total_paid_tax_excl: String,
    val total_paid_tax_incl: String,
    val total_products: String,
    val total_products_wt: String,
    val total_shipping: String,
    val total_shipping_tax_excl: String,
    val total_shipping_tax_incl: String,
    val total_wrapping: String,
    val total_wrapping_tax_excl: String,
    val total_wrapping_tax_incl: String,
    val valid: String,

    var order_status: OrderState,
    var address_invoice: Addresse,
    var address_delivery: Addresse
)

data class OrderRow(
    val id: String,
    val product_attribute_id: String,
    val product_ean13: String,
    val product_id: String,
    val product_name: String,
    val product_price: String,
    val product_quantity: String,
    val product_reference: String,
    val product_upc: String,
    val unit_price_tax_excl: String,
    val unit_price_tax_incl: String
)

data class Associations(
    val order_rows: List<OrderRow>
)