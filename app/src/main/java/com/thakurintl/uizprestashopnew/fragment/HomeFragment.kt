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
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var productListModel: ProductListModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //This lets know that fragment also has menu and inflate menu
        setHasOptionsMenu(true)

//        linearLayoutManager = LinearLayoutManager(context)
//        categories_recyclerview.layoutManager = linearLayoutManager


//        view.txt_view_all_category.setOnClickListener {
//            //            Toast.makeText(context, "All List of Categories", Toast.LENGTH_SHORT).show()
//
//            startActivity(Intent(context, AllCategoryActivity::class.java))
//        }

//        view.txt_view_all_employee.setOnClickListener {
//            //            Toast.makeText(context, "All List of Employee", Toast.LENGTH_SHORT).show()
//
//            startActivity(Intent(context, AllEmployeeActivity::class.java))
//        }


        /**
         * Getting all the products to display in
         * product list
         */
        getProductList()

        /**
         * Now getting categories list
         */
        //getCategoriesList()

        return view
    }


    private fun getProductList() {
        val productDetailsObject = ProductDetailsObject()

        productDetailsObject.getAllProductList(context!!)

        productDetailsObject.setAllProductSuccessListener(object :
            ProductDetailsObject.AllProductSuccessCallback {
            override fun onSuccess(productListModel: ProductListModel) {
                this@HomeFragment.productListModel = productListModel

                getDefaultCombination(productListModel.products)
            }

        })

    }

    private fun getDefaultCombination(products: List<Product>) {
        val comDM = CombinationDetailsObject()
        comDM.getAllCombinations(context!!)

        comDM.setOnAllCombinationListener(object : CombinationDetailsObject.AllCombinationCallback {
            override fun onSuccess(combinationList: CombinationListModel) {

                for (i in products.indices) {

                    val idDefComb = products[i].id_default_combination

                    for (j in 0 until combinationList.combinations.size) {

                        if (idDefComb == combinationList.combinations[j].id) {

                            products[i].price = combinationList.combinations[j].price

                        }

                    }

                }
                showDataInRecyclerView(products)
            }

        })

    }


    private fun showDataInRecyclerView(products: List<Product>) {

        if (!isFragmentDetached) {

            if (home_frag_progress.isVisible)
                home_frag_progress.visibility = View.GONE

            val productAdapter = AllProductRecyclerAdapter(products)

            employee_recyclerview.layoutManager = LinearLayoutManager(context)
            employee_recyclerview.adapter = productAdapter

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
