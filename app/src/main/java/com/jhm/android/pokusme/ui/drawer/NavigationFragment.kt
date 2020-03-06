package com.jhm.android.pokusme.ui.drawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.R.layout.fragment_navigation
import kotlinx.android.synthetic.main.fragment_navigation.*


class NavigationFragment : BottomSheetDialogFragment() {
    private lateinit var navigationViewModel: NavigationViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(fragment_navigation, container, false)
    
        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        navigationViewModel.displayName.observe(viewLifecycleOwner, Observer {
            text_navigation_displayName.text = it
        })
        navigationViewModel.email.observe(viewLifecycleOwner, Observer {
            text_navigation_email.text = it
        })
        
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_navigation)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_navigation_signOut -> {
                    Log.d("hmlog","signout")
                    true
                }
                else -> false
            }
        }
        
        return view
    }
}
