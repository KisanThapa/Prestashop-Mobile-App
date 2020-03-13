package com.thakurintl.uizprestashopnew.modelclass

data class ProductOptionValueDetailModel(
    val product_option_values: List<ProductOptionValueSingle>
)

data class ProductOptionValueSingle(
    val color: String,
    val id: Int,
    val id_attribute_group: String,
    val name: String,
    val position: String
)