package com.thakurintl.uizprestashopnew.modelclass

data class ProductOptionModel(
    val product_options: List<ProductOptionList>
)

data class ProductOptionList(
    val associations: AssociationsValues,
    val group_type: String,
    val id: Int,
    val is_color_group: String,
    val name: String,
    val position: String,
    val public_name: String
)

data class AssociationsValues(
    val product_option_values: List<ProductOptionVal>
)

data class ProductOptionVal(
    val id: String
)