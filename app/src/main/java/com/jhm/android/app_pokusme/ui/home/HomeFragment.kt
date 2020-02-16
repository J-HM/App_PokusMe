package com.jhm.android.app_pokusme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.app_pokusme.R
import com.jhm.android.app_pokusme.adapter.LatestVideoAdapter
import com.jhm.android.app_pokusme.data.LatestVideoData

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private val latestVideos = ArrayList<LatestVideoData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            // text_home.text = it
        })
        
        latestVideos.add(LatestVideoData("test1"))
        latestVideos.add(LatestVideoData("test2"))
        latestVideos.add(LatestVideoData("test3"))
        latestVideos.add(LatestVideoData("test4"))
        latestVideos.add(LatestVideoData("test5"))
        latestVideos.add(LatestVideoData("test6"))
        latestVideos.add(LatestVideoData("test7"))
        latestVideos.add(LatestVideoData("test8"))
        latestVideos.add(LatestVideoData("test9"))
        latestVideos.add(LatestVideoData("test10"))
        latestVideos.add(LatestVideoData("test11"))
        latestVideos.add(LatestVideoData("test12"))
        latestVideos.add(LatestVideoData("test13"))
        latestVideos.add(LatestVideoData("test14"))
        latestVideos.add(LatestVideoData("test15"))
        
        val recyclerLatestVideo = view.findViewById(R.id.recycler_home_latestVideo) as RecyclerView
        recyclerLatestVideo.apply {
            this.layoutManager = LinearLayoutManager(activity).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = LatestVideoAdapter(latestVideos)
        }
    
        val recyclerTest2 = view.findViewById(R.id.recycler_home_test2) as RecyclerView
        recyclerTest2.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = LatestVideoAdapter(latestVideos)
        }
        
        return view
    }
}