package com.jhm.android.app_pokusme.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jhm.android.app_pokusme.R
import com.jhm.android.app_pokusme.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.name.observe(viewLifecycleOwner, Observer {
            text_profile_name.text = it
        })
        
        getUserProfile { updateUI(it) }
    
        val buttonSignOut = view.findViewById(R.id.button_profile_signout) as ImageButton
        val buttonEdit = view.findViewById(R.id.button_profile_edit) as ImageButton
        
        buttonSignOut.setOnClickListener { signOut() }
        buttonEdit.setOnClickListener { Log.d("jhmlog", "edit") }
        
        return view
    }
    
    private fun updateUI(user: FirebaseUser) {
//        user.displayName
//        user.email
//        user.isEmailVerified
//        user.uid
        profileViewModel.name.value = user.displayName
    }
    
    private fun getUserProfile(_updateUI: (FirebaseUser) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { _updateUI(it) }
    }
    
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
}