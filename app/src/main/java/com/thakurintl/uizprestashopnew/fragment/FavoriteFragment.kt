package com.thakurintl.uizprestashopnew.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thakurintl.uizprestashopnew.R

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        //This lets know that fragment also has menu and inflate menu
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


}
