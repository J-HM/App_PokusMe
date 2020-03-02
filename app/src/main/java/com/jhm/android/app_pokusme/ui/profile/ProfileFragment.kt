package com.jhm.android.app_pokusme.ui.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.jhm.android.app_pokusme.MainActivity
import com.jhm.android.app_pokusme.R
import com.jhm.android.app_pokusme.data.UserData
import com.jhm.android.app_pokusme.ui.auth.LoginActivity
import com.jhm.android.app_pokusme.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var currentUser: UserData
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.displayName.observe(viewLifecycleOwner, Observer {
            text_profile_name.text = it
        })
        profileViewModel.email.observe(viewLifecycleOwner, Observer {
            text_profile_email.text = it
        })
        
        val mainActivity: MainActivity? = activity as MainActivity?
        currentUser = mainActivity!!.currentUser
        userDataUpdate()
        
        val buttonSignOut = view.findViewById(R.id.button_profile_signOut) as ImageButton
        val buttonEdit = view.findViewById(R.id.button_profile_edit) as ImageButton
        
        buttonSignOut.setOnClickListener { signOut() }
        buttonEdit.setOnClickListener { Log.d("jhmlog", "edit") }
        
        val toolbar = view.findViewById(R.id.toolbar_profile_toolbar) as Toolbar
        toolbar.menu.findItem(R.id.appbar_setting).setOnMenuItemClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
            false
        }
        return view
    }
    
    private fun userDataUpdate() {
        profileViewModel.displayName.value = this.currentUser.displayName
        profileViewModel.email.value = this.currentUser.email
        profileViewModel.isEmailVerified.value = this.currentUser.isEmailVerified
    }
    
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
}