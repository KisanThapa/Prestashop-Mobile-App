package com.thakurintl.uizprestashopnew.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.fragment.*
import com.thakurintl.uizprestashopnew.objects.MySharedPreferences
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity() {

    var MENU_FLAG = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Toolbar
        setSupportActionBar(main_toolbar)
        main_toolbar.title = "V-E Application"

        //Creating badge using BadgeDrawable (class of the material library
        // com.google.android.material:material:1.1.0-beta02)

        //val badgeDrawable: BadgeDrawable =
        bottom_navigation.getOrCreateBadge(R.id.action_cart).backgroundColor = Color.WHITE
        bottom_navigation.getOrCreateBadge(R.id.action_cart).badgeTextColor = Color.BLACK
        bottom_navigation.getOrCreateBadge(R.id.action_cart).number =
            MySharedPreferences.getCartLength(applicationContext)



        decideFragment(1)


        bottom_navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.action_home -> {
                    MENU_FLAG = 1
                    decideFragment(1)

                    true
                }
                R.id.action_search -> {
                    MENU_FLAG = 2
                    decideFragment(2)

                    true
                }
                R.id.action_cart -> {
                    MENU_FLAG = 3
                    //Removing visible badge
                    //bottom_navigation.removeBadge(R.id.action_cart)

                    decideFragment(3)

                    true
                }
//                R.id.action_favorite -> {
//                    MENU_FLAG = 4
//                    decideFragment(4)
//
//                    true
//                }
                R.id.action_account -> {
                    MENU_FLAG = 5
                    decideFragment(5)

                    true
                }
                else ->
                    decideFragment(1)
            }
        }
    }

    private fun decideFragment(i: Int): Boolean {

        lateinit var frag: Fragment

        return when (i) {

            1 -> {
                frag = HomeFragment()
                changeFragment(frag)
                main_toolbar.title = "My Shop"
                true
            }

            2 -> {
                frag = SearchFragment()
                changeFragment(frag)
                main_toolbar.title = "Catalog"
                true
            }

            3 -> {
                frag = CartFragment()
                main_toolbar.title = "Cart"
                changeFragment(frag)
                true
            }

//            4 -> {
//                frag = FavoriteFragment()
//                main_toolbar.title = "Favorite"
//                changeFragment(frag)
//                true
//            }

            5 -> {
                frag = AccountFragment()
                main_toolbar.title = "Personal Info"
                changeFragment(frag)
                true
            }
            else ->
                false
        }
    }

    private fun changeFragment(frag: Fragment) {

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame_layout, frag)
        transaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        return when (MENU_FLAG) {
            5 -> {
                try {
                    menuInflater.inflate(R.menu.setting_menu, menu)
                } catch (e: Exception) {
                }

                true
            }

            2 -> {
                try {
                    menuInflater.inflate(R.menu.search_menu, menu)
                } catch (e: Exception) {
                }

                true
            }

            4 -> {
                true
            }

            3 -> {
                true
            }

            else -> {
                try {
                    menuInflater.inflate(R.menu.main_menu, menu)
                } catch (e: Exception) {
                }

                true
            }
        }


        //return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {

//            R.id.menu_cart -> {
//                Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, CartActivity::class.java))
//                true
//            }

            //main menu items
            R.id.itm1 -> {
                Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.menu_exit -> {
                Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
                true
            }

            //Account Settings menu
            R.id.action_setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }

            //Search Menu Actions
            R.id.action_menu_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }

            else -> {
                false
            }
        }
    }
}
