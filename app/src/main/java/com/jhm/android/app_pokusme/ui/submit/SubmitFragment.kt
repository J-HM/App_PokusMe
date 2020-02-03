package com.jhm.android.app_pokusme.ui.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.fragment_submit.*

class SubmitFragment : Fragment() {

    private lateinit var submitViewModel: SubmitViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        submitViewModel = ViewModelProvider(this).get(SubmitViewModel::class.java)
        submitViewModel.text.observe(this, Observer {
            text_submit.text = it
        })
        return inflater.inflate(R.layout.fragment_submit, container, false)
    }
}