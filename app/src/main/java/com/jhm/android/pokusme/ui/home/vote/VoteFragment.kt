package com.jhm.android.pokusme.ui.home.vote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhm.android.pokusme.R


class VoteFragment : Fragment() {
    private lateinit var voteViewModel: VoteViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vote, container, false)
        voteViewModel = ViewModelProvider(this).get(VoteViewModel::class.java)
        voteViewModel.text.observe(viewLifecycleOwner, Observer {
            // text_home.text = it
        })

        return view
    }

}