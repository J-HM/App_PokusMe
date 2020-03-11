package com.jhm.android.pokusme.ui.home.invest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhm.android.pokusme.R


class InvestFragment : Fragment() {
    private lateinit var investViewModel: InvestViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_invest, container, false)
        investViewModel = ViewModelProvider(this).get(InvestViewModel::class.java)
        investViewModel.text.observe(viewLifecycleOwner, Observer {
            // text_home.text = it
        })
        
        return view
    }

}