package com.jhm.android.pokusme.ui.drawer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.jhm.android.pokusme.MainActivity
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.R.layout.fragment_navigation
import com.jhm.android.pokusme.data.UserData
import com.jhm.android.pokusme.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_navigation.*


class NavigationFragment : BottomSheetDialogFragment() {
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var currentUser: UserData
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(fragment_navigation, container, false)
        
        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        enrollObserve()
        
        val mainActivity: MainActivity? = activity as MainActivity?

        mainActivity!!.currentUser?.let {
            currentUser = it
            updateUserData()
        }
        
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_navigation)
        setNavigationItemSelectedListener(navigationView)
        
        return view
    }
    
    private fun setNavigationItemSelectedListener(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_navigation_signOut -> {
                    signOut()
                    true
                }
                R.id.menu_navigation_profileEdit -> {
                    Log.d("jhmlog", "menu_navigation_profileEdit")
                    true
                }
                R.id.menu_navigation_setting -> {
                    Log.d("jhmlog", "menu_navigation_signOut")
                    true
                }
                else -> false
            }
        }
    }
    
    private fun enrollObserve() {
        navigationViewModel.displayName.observe(viewLifecycleOwner, Observer {
            text_navigation_displayName.text = it
        })
        navigationViewModel.email.observe(viewLifecycleOwner, Observer {
            text_navigation_email.text = it
        })
    }
    
    private fun updateUserData() {
        navigationViewModel.displayName.value = this.currentUser.displayName
        navigationViewModel.email.value = this.currentUser.email
    }
    
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
}
