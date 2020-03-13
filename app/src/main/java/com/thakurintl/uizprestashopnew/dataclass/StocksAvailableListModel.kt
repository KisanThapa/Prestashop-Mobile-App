package com.thakurintl.uizprestashopnew.dataclass

data class StocksAvailableListModel(var stock_availables: List<StockAvailableDetails>)


data class StockAvailableDetails(
    val depends_on_stock: String,
    val id: Int,
    val id_product: String,
    val id_product_attribute: String,
    val id_shop: String,
    val id_shop_group: String,
    val out_of_stock: String,
    val quantity: String
)