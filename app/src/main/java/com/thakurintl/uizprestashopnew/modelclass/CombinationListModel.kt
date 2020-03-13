package com.thakurintl.uizprestashopnew.modelclass

data class CombinationListModel(
    val combinations: MutableList<Combination>
)

data class Combination(
    val associations: AssociationsLst,
    val available_date: String,
    val default_on: String,
    val ean13: String,
    val ecotax: String,
    val id: Int,
    val id_product: String,
    val location: String,
    val minimal_quantity: String,
    val price: String,
    val quantity: String,
    val reference: String,
    val supplier_reference: String,
    val unit_price_impact: String,
    val upc: String,
    val weight: String,
    val wholesale_price: String
)

data class AssociationsLst(
    val product_option_values: List<ProductOptionValueLst>
)

data class ProductOptionValueLst(
    val id: String
)
