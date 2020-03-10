package com.jhm.android.pokusme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jhm.android.pokusme.MainActivity
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.ui.submit.SubmitFragment


class HomeFragment : Fragment() {
    private var fragmentTransaction: FragmentTransaction? = null

    private var popularFragment: HomeFragment? = null
    private var voteFragment: SubmitFragment? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        popularFragment = HomeFragment()
        voteFragment = SubmitFragment()
    
//        fragmentTransaction = childFragmentManager.beginTransaction()
//        homeFragmentManager = mainActivity!!.supportFragmentManager
//
//        homeFragmentManager?.beginTransaction()?.add(R.id.frame_home, voteFragment!!)?.commit()
//        homeFragmentManager?.beginTransaction()?.add(R.id.frame_home, popularFragment!!)?.commit()

//        val tabBarHome = view.findViewById<TabLayout>(R.id.tabbar_home)
//        tabBarHome.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                if (tab.position == 0) {
//                    homeFragmentManager?.beginTransaction()?.show(popularFragment!!)?.commit()
//                } else if (tab.position == 1) {
//                    homeFragmentManager?.beginTransaction()?.show(voteFragment!!)?.commit()
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//                if (tab.position == 0) {
//                    homeFragmentManager?.beginTransaction()?.hide(popularFragment!!)?.commit()
//                } else if (tab.position == 1) {
//                    homeFragmentManager?.beginTransaction()?.hide(voteFragment!!)?.commit()
//                }
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//        })
        
        return view
    }
    
}