package com.thakurintl.uizprestashopnew.dataclass

data class CustomerResponse(
    val customers: List<CustomerModel>
)


data class CustomerModel(
    val id: Int,
    val id_default_group: Int,
    val id_lang: Int,
    val newsletter_date_add: String,
    val ip_registration_newsletter: String,
    val last_passwd_gen: String,
    val secure_secure_key: String,
    val deleted: Int,
    val passwd: String,
    val lastname: String,
    val firstname: String,
    val email: String,
    val id_gender: Int,
    val birthday: String,
    val newsletter: Int,
    val optin: Int,
    val website: String,
    val company: String,
    val siret: String,
    val ape: String,
    val outstanding_allow_amount: Double,
    val show_public_prices: Int,
    val id_risk: Int,
    val max_payment_days: Int,
    val active: Int,
    val note: String,
    val is_guest: Int,
    val id_shop: Int,
    val id_shop_group: Int,
    val date_add: String,
    val date_upd: String,
    val associations: AssociationsCustomer

)


data class AssociationsCustomer(

    val groups: List<Groups>

)


data class Groups(

    val id: Int
)
