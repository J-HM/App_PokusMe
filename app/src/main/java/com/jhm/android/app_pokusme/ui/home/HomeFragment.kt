package com.jhm.android.app_pokusme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    
    private lateinit var homeViewModel: HomeViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            text_home.text = it
        })
    
        homeViewModel.number.observe(viewLifecycleOwner, Observer {
            button_home_test.text = it.toString()
        })
        
        
        
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}