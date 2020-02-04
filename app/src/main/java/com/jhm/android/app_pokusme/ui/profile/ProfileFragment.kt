package com.jhm.android.app_pokusme.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.jhm.android.app_pokusme.MainActivity
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            text_profile.text = it
        })
        
        // button_profile_signOut.setOnClickListener { signOut() }
        
        // updateUI()
        
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    private fun updateUI() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            Log.d("jhmlog", "$user")
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            
            val emailVerified = user.isEmailVerified
            
            val uid = user.uid
            //text_profile_name.text = name
        }
    }
    
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        (activity as MainActivity?)?.startLoginActivity()
    }
}