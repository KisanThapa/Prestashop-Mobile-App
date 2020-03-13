package com.thakurintl.uizprestashopnew.dataclass

data class CartListResponseModel(val carts: List<Cart>)

data class Cart(
    val allow_seperated_package: String,
    val associations: AssociationsCart,
    val date_add: String,
    val date_upd: String,
    val delivery_option: String,
    val gift: String,
    val gift_message: String,
    val id: Int,
    val id_address_delivery: String,
    val id_address_invoice: String,
    val id_carrier: String,
    val id_currency: String,
    val id_customer: String,
    val id_guest: String,
    val id_lang: String,
    val id_shop: String,
    val id_shop_group: String,
    val mobile_theme: String,
    val recyclable: String,
    val secure_key: String
)

data class AssociationsCart(
    val cart_rows: List<CartRow>
)

data class CartRow(
    val id_address_delivery: String,
    val id_product: String,
    val id_product_attribute: String,
    val quantity: String
)
