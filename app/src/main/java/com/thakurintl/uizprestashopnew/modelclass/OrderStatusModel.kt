package com.thakurintl.uizprestashopnew.modelclass

data class OrderStatusModel(
    val order_states: MutableList<OrderState>
)

data class OrderState(
    val color: String,
    val deleted: String,
    val delivery: String,
    val hidden: String,
    val id: Int,
    val invoice: String,
    val logable: String,
    val module_name: String,
    val name: String,
    val paid: String,
    val pdf_delivery: String,
    val pdf_invoice: String,
    val send_email: String,
    val shipped: String,
    val template: String,
    val unremovable: String
)