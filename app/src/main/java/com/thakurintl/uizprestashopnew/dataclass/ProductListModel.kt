package com.thakurintl.uizprestashopnew.dataclass

data class ProductListModel(
    val products: List<Product>
)

data class Product(
    val active: String,
    val additional_shipping_cost: String,
    val advanced_stock_management: String,
    val associations: Associations,
    val available_date: String,
    val available_for_order: String,
    val available_later: String,
    val available_now: String,
    val cache_default_attribute: String,
    val cache_has_attachments: String,
    val cache_is_pack: String,
    val condition: String,
    val customizable: String,
    val date_add: String,
    val date_upd: String,
    val depth: String,
    val description: String,
    val description_short: String,
    val ean13: String,
    val ecotax: String,
    val height: String,
    val id: Int,
    val id_category_default: String,
    val id_default_combination: Int,
    val id_default_image: String,
    val id_manufacturer: String,
    val id_product_redirected: String,
    val id_shop_default: String,
    val id_supplier: String,
    val id_tax_rules_group: String,
    val indexed: String,
    val is_virtual: String,
    val link_rewrite: String,
    val location: String,
    val manufacturer_name: String,
    val meta_description: String,
    val meta_keywords: String,
    val meta_title: String,
    val minimal_quantity: String,
    val name: String,
    val new: Any,
    val on_sale: String,
    val online_only: String,
    val pack_stock_type: String,
    val position_in_category: String,
    var price: String,
    val quantity: String,
    val quantity_discount: String,
    val redirect_type: String,
    val reference: String,
    val show_price: String,
    val supplier_reference: String,
    val text_fields: String,
    val type: String,
    val unit_price_ratio: String,
    val unity: String,
    val upc: String,
    val uploadable_files: String,
    val visibility: String,
    val weight: String,
    val wholesale_price: String,
    val width: String,



    var cartQuantity: String,
    var stockQuantityAvailable: String
)

data class Associations(
    val categories: List<Category>,
    val images: List<Image>,
    val product_features: List<ProductFeature>,
    val stock_availables: List<StockAvailable>
)

data class Category(
    val id: String
)

data class Image(
    val id: String
)

data class ProductFeature(
    val id: String,
    val id_feature_value: String
)

data class StockAvailable(
    val id: String,
    val id_product_attribute: String
)