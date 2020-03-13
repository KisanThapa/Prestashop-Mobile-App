package com.thakurintl.uizprestashopnew.dataclass

data class AddressListResponseModel(
    val addresses: List<Addresse>
)

data class Addresse(
    val address1: String,
    val address2: String,
    val alias: String,
    val city: String,
    val company: String,
    val date_add: String,
    val date_upd: String,
    val deleted: String,
    val dni: String,
    val firstname: String,
    val id: Int,
    val id_country: String,
    val id_customer: String,
    val id_manufacturer: String,
    val id_state: String,
    val id_supplier: String,
    val id_warehouse: String,
    val lastname: String,
    val other: String,
    val phone: String,
    val phone_mobile: String,
    val postcode: String,
    val vat_number: String
)