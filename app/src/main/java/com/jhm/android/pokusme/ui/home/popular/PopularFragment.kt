package com.jhm.android.pokusme.ui.home.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.adapter.BestVideoAdapter
import com.jhm.android.pokusme.adapter.LatestVideoAdapter
import com.jhm.android.pokusme.data.VideoData
import com.jhm.android.pokusme.ui.home.HomeViewModel


class PopularFragment : Fragment() {
    private lateinit var popularViewModel: PopularViewModel
//    private val latestVideos = ArrayList<VideoData>()
//    private val bestVideos = ArrayList<VideoData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        popularViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        popularViewModel.text.observe(viewLifecycleOwner, Observer {
            // text_home.text = it
        })
        
//        latestVideos.add(VideoData("test1", "UMW9NmlX1ws"))
//        latestVideos.add(VideoData("test2", "UMW9NmlX1ws"))
//        latestVideos.add(VideoData("test3", "UMW9NmlX1ws"))
//        latestVideos.add(VideoData("test4", "UMW9NmlX1ws"))
//        latestVideos.add(VideoData("test5", "UMW9NmlX1ws"))
//
//        bestVideos.add(VideoData("test1", "UMW9NmlX1ws"))
//        bestVideos.add(VideoData("test2", "UMW9NmlX1ws"))
//        bestVideos.add(VideoData("test3", "UMW9NmlX1ws"))
//        bestVideos.add(VideoData("test4", "UMW9NmlX1ws"))
//        bestVideos.add(VideoData("test5", "UMW9NmlX1ws"))
    
//        val recyclerLatestVideo = view.findViewById(R.id.recycler_home_latestVideo) as RecyclerView
//        val recyclerBestVideo = view.findViewById(R.id.recycler_home_bestVideo) as RecyclerView
//
//        recyclerLatestVideo.apply {
//            this.layoutManager = LinearLayoutManager(activity).also {
//                it.orientation = LinearLayoutManager.HORIZONTAL
//            }
//            this.adapter = LatestVideoAdapter(latestVideos)
//        }
//        recyclerBestVideo.apply {
//            this.layoutManager = LinearLayoutManager(activity)
//            this.adapter = BestVideoAdapter(bestVideos)
//        }
        
        return view
    }

}