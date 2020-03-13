package com.thakurintl.uizprestashopnew.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.activity.ProductItemActivity
import com.thakurintl.uizprestashopnew.adapter.AllProductRecyclerAdapter
import com.thakurintl.uizprestashopnew.dataclass.Product
import com.thakurintl.uizprestashopnew.dataclass.ProductListModel
import com.thakurintl.uizprestashopnew.modelclass.CombinationListModel
import com.thakurintl.uizprestashopnew.objects.CombinationDetailsObject
import com.thakurintl.uizprestashopnew.objects.ProductDetailsObject
import com.thakurintl.uizprestashopnew.objects.UtilityObjects
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //This lets know that fragment also has menu and inflate menu
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_search, container, false)


        //getAllProducts()

        getProductList()

        return view
    }

    private lateinit var productListModel: ProductListModel

    private fun getProductList() {
//        if (UtilityObjects.savedProducts!!.products.isNullOrEmpty()) {
            val productDetailsObject = ProductDetailsObject()

            productDetailsObject.getAllProductList(context!!)

            productDetailsObject.setAllProductSuccessListener(object :
                ProductDetailsObject.AllProductSuccessCallback {
                override fun onSuccess(productListModel: ProductListModel) {
                    this@SearchFragment.productListModel = productListModel

                    getDefaultCombination(productListModel.products)
                }

            })

//        } else {
//            productListModel = UtilityObjects.savedProducts!!
//
//            getDefaultCombination(productListModel.products)
//        }

    }

    private fun getDefaultCombination(products: List<Product>) {

//        if (UtilityObjects.savedCombination!!.combinations.isNullOrEmpty()) {

            val comDM = CombinationDetailsObject()
            comDM.getAllCombinations(context!!)

            comDM.setOnAllCombinationListener(object :
                CombinationDetailsObject.AllCombinationCallback {
                override fun onSuccess(combinationList: CombinationListModel) {

                    for (i in products.indices) {

                        val idDefComb = products[i].id_default_combination

                        for (j in 0 until combinationList.combinations.size) {

                            if (idDefComb == combinationList.combinations[j].id) {

                                products[i].price = combinationList.combinations[j].price

                            }
                        }
                    }
                    displayDataInRecyclerView(products)
                }

            })
//        } else {
//            for (i in products.indices) {
//
//                val idDefComb = products[i].id_default_combination
//
//                for (j in 0 until UtilityObjects.savedCombination!!.combinations.size) {
//
//                    if (idDefComb == UtilityObjects.savedCombination!!.combinations[j].id) {
//
//                        products[i].price = UtilityObjects.savedCombination!!.combinations[j].price
//
//                    }
//                }
//            }
//            displayDataInRecyclerView(products)
//        }

    }

    private fun displayDataInRecyclerView(productList: List<Product>) {

        if (!isFragmentDetached) {

            if (search_frag_progress.isVisible)
                search_frag_progress.visibility = View.GONE

            val productAdapter = AllProductRecyclerAdapter(productList)

            search_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
            search_fragment_recycler_view.adapter = productAdapter


            productAdapter.setOnItemClickListener(object :
                AllProductRecyclerAdapter.ItemClickListener {
                override fun onItemClick(productId: Int, productName: String) {
                    val intent = Intent(context, ProductItemActivity::class.java)
                    intent.putExtra("PID", productId)
                    intent.putExtra("PNAME", productName)
                    startActivity(intent)
                }

            })

        }

    }

    private var isFragmentDetached: Boolean = false

    override fun onDetach() {
        super.onDetach()

        isFragmentDetached = true
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)

        isFragmentDetached = false
    }

}
