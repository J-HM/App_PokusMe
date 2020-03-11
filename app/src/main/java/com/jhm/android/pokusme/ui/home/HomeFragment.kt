package com.jhm.android.pokusme.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.ui.home.invest.InvestFragment
import com.jhm.android.pokusme.ui.home.popular.PopularFragment
import com.jhm.android.pokusme.ui.home.vote.VoteFragment
import kotlinx.android.synthetic.main.fragment_invest.*
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_vote.*


class HomeFragment : Fragment() {
    private var homeFragmentManager: FragmentManager? = null
    
    private var popularFragment: PopularFragment = PopularFragment()
    private var voteFragment: VoteFragment = VoteFragment()
    private var investFragment: InvestFragment = InvestFragment()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        homeFragmentManager = childFragmentManager
        
        homeFragmentManager?.beginTransaction()?.add(R.id.frame_home, popularFragment)?.commit()
        homeFragmentManager?.beginTransaction()?.add(R.id.frame_home, voteFragment)?.commit()
        homeFragmentManager?.beginTransaction()?.add(R.id.frame_home, investFragment)?.commit()
        homeFragmentManager?.beginTransaction()?.hide(voteFragment)?.commit()
        homeFragmentManager?.beginTransaction()?.hide(investFragment)?.commit()
        
        val tabBarHome = view.findViewById<TabLayout>(R.id.tabbar_home)
        tabBarHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> homeFragmentManager?.beginTransaction()?.show(popularFragment)?.commit()
                    1 -> homeFragmentManager?.beginTransaction()?.show(voteFragment)?.commit()
                    2 -> homeFragmentManager?.beginTransaction()?.show(investFragment)?.commit()
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> homeFragmentManager?.beginTransaction()?.hide(popularFragment)?.commit()
                    1 -> homeFragmentManager?.beginTransaction()?.hide(voteFragment)?.commit()
                    2 -> homeFragmentManager?.beginTransaction()?.hide(investFragment)?.commit()
                }
            }
            
            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> nestedScroll_popular.fullScroll(View.FOCUS_UP)
                    1 -> nestedScroll_vote.fullScroll(View.FOCUS_UP)
                    2 -> nestedScroll_invest.fullScroll(View.FOCUS_UP)
                }
            }
        })
        
        return view
    }
    
}