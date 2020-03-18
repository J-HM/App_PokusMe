package com.jhm.android.pokusme.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.ui.main.invest.InvestFragment
import com.jhm.android.pokusme.ui.main.home.HomeFragment
import com.jhm.android.pokusme.ui.main.vote.VoteFragment
import kotlinx.android.synthetic.main.fragment_invest.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_vote.*


class MainFragment : Fragment() {
    private var mainFragmentManager: FragmentManager? = null
    
    private var homeFragment: HomeFragment = HomeFragment()
    private var voteFragment: VoteFragment = VoteFragment()
    private var investFragment: InvestFragment = InvestFragment()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        
        mainFragmentManager = childFragmentManager
        
        mainFragmentManager?.beginTransaction()?.add(R.id.frame_main, homeFragment)?.commit()
        mainFragmentManager?.beginTransaction()?.add(R.id.frame_main, voteFragment)?.commit()
        mainFragmentManager?.beginTransaction()?.add(R.id.frame_main, investFragment)?.commit()
        mainFragmentManager?.beginTransaction()?.hide(voteFragment)?.commit()
        mainFragmentManager?.beginTransaction()?.hide(investFragment)?.commit()
        
        val tabBarHome = view.findViewById<TabLayout>(R.id.tabbar_main)
        tabBarHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> mainFragmentManager?.beginTransaction()?.show(homeFragment)?.commit()
                    1 -> mainFragmentManager?.beginTransaction()?.show(voteFragment)?.commit()
                    2 -> mainFragmentManager?.beginTransaction()?.show(investFragment)?.commit()
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> mainFragmentManager?.beginTransaction()?.hide(homeFragment)?.commit()
                    1 -> mainFragmentManager?.beginTransaction()?.hide(voteFragment)?.commit()
                    2 -> mainFragmentManager?.beginTransaction()?.hide(investFragment)?.commit()
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