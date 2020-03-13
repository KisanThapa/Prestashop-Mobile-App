package com.thakurintl.uizprestashopnew.dataclass

data class CategoriesResponseModel(
    val categories: List<CategoryName>
)

data class CategoryName(
    val active: String,
    val associations: AssociationsCategory,
    val date_add: String,
    val date_upd: String,
    val description: String,
    val id: Int,
    val id_parent: String,
    val id_shop_default: String,
    val is_root_category: String,
    val level_depth: String,
    val link_rewrite: String,
    val meta_description: String,
    val meta_keywords: String,
    val meta_title: String,
    val name: String,
    val nb_products_recursive: String,
    val position: String
)

data class AssociationsCategory(
    val categories: List<CategoriesName>,
    val products: List<ProductCategory>
)

data class ProductCategory(
    val id: String
)

data class CategoriesName(
    val id: String
)
