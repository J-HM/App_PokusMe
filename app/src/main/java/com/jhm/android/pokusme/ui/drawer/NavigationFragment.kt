package com.jhm.android.pokusme.ui.drawer

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
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
import com.jhm.android.pokusme.ui.drawer.profile.ProfileActivity
import com.jhm.android.pokusme.ui.drawer.profileEdit.ProfileEditActivity
import com.jhm.android.pokusme.ui.drawer.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_navigation.*


class NavigationFragment : BottomSheetDialogFragment() {
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var currentUser: UserData
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(fragment_navigation, container, false)
        
        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        enrollObserve()
        
        (activity as MainActivity?)!!.currentUser?.let {
            currentUser = it
            updateUserData()
        }
        
        setNavigationItemSelectedListener(view.findViewById<NavigationView>(R.id.navigation_navigation))
        
        view.findViewById<ImageButton>(R.id.button_navigation_profileEdit).setOnClickListener {
            startActivity(Intent(activity, ProfileEditActivity::class.java))
        }
        
        view.findViewById<ImageView>(R.id.image_navigation_default).background = ShapeDrawable(OvalShape())
        
        return view
    }
    
    private fun setNavigationItemSelectedListener(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_navigation_signOut -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(activity, LoginActivity::class.java))
                    activity?.finish()
                    true
                }
                R.id.menu_navigation_profile -> {
                    startActivity(Intent(activity, ProfileActivity::class.java))
                    true
                }
                R.id.menu_navigation_setting -> {
                    startActivity(Intent(activity, SettingActivity::class.java))
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

}
