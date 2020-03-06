package com.jhm.android.pokusme.ui.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.UserData
import kotlinx.android.synthetic.main.fragment_submit.*


class SubmitFragment : Fragment() {
    private lateinit var submitViewModel: SubmitViewModel
    private lateinit var currentUser: UserData
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_submit, container, false)
        submitViewModel = ViewModelProvider(this).get(SubmitViewModel::class.java)
        submitViewModel.displayName.observe(viewLifecycleOwner, Observer {
            text_submit_test.text = it
        })
        
        return view
    }
    
    private fun userDataUpdate() {
        submitViewModel.displayName.value = this.currentUser.displayName
        submitViewModel.email.value = this.currentUser.email
        submitViewModel.isEmailVerified.value = this.currentUser.isEmailVerified
    }
    
}