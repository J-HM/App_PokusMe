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
import com.jhm.android.app_pokusme.adapter.BestVideoAdapter
import com.jhm.android.app_pokusme.adapter.LatestVideoAdapter
import com.jhm.android.app_pokusme.data.VideoData

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private val latestVideos = ArrayList<VideoData>()
    private val bestVideos = ArrayList<VideoData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            // text_home.text = it
        })
        
        latestVideos.add(VideoData("test1"))
        latestVideos.add(VideoData("test2"))
        latestVideos.add(VideoData("test3"))
        latestVideos.add(VideoData("test4"))
        latestVideos.add(VideoData("test5"))
        latestVideos.add(VideoData("test6"))
        latestVideos.add(VideoData("test7"))
        latestVideos.add(VideoData("test8"))
        latestVideos.add(VideoData("test9"))
    
        bestVideos.add(VideoData("test1"))
        bestVideos.add(VideoData("test2"))
        bestVideos.add(VideoData("test3"))
        bestVideos.add(VideoData("test4"))
        bestVideos.add(VideoData("test5"))
        
        val recyclerLatestVideo = view.findViewById(R.id.recycler_home_latestVideo) as RecyclerView
        val recyclerBestVideo = view.findViewById(R.id.recycler_home_bestVideo) as RecyclerView

        recyclerLatestVideo.apply {
            this.layoutManager = LinearLayoutManager(activity).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.adapter = LatestVideoAdapter(latestVideos)
        }
        recyclerBestVideo.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = BestVideoAdapter(bestVideos)
        }
        
        return view
    }
}