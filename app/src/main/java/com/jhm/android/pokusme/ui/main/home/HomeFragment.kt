package com.jhm.android.pokusme.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.adapter.LatestVideoAdapter
import com.jhm.android.pokusme.adapter.PopularVideoAdapter
import com.jhm.android.pokusme.data.VideoData


class HomeFragment : Fragment() {
    private val latestVideos = ArrayList<VideoData>()
    private val popularVideos = ArrayList<VideoData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        latestVideos.add(
            VideoData(
                "최신 비디오1",
                "https://firebasestorage.googleapis.com/v0/b/pokus-6f2ae.appspot.com/o/video_idea_1.mp4?alt=media&token=52175d74-6ebc-47d6-ad92-cd3c447604fe".toUri(),
                "https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg".toUri()
            )
        )
        latestVideos.add(
            VideoData(
                "최신 비디오2",
                "https://firebasestorage.googleapis.com/v0/b/pokus-6f2ae.appspot.com/o/video_idea_2.mp4?alt=media&token=509a9cc0-7fb0-46c9-b330-823bf6836da0".toUri(),
                "https://image.shutterstock.com/image-photo/colorful-flower-on-dark-tropical-260nw-721703848.jpg".toUri()
            )
        )

        popularVideos.add(
            VideoData(
                "인기1",
                "https://firebasestorage.googleapis.com/v0/b/pokus-6f2ae.appspot.com/o/video_idea_2.mp4?alt=media&token=509a9cc0-7fb0-46c9-b330-823bf6836da0".toUri(),
                "https://image.shutterstock.com/image-photo/colorful-flower-on-dark-tropical-260nw-721703848.jpg".toUri()
            )
        )
        popularVideos.add(
            VideoData(
                "인기2 인기2 인기2",
                "https://firebasestorage.googleapis.com/v0/b/pokus-6f2ae.appspot.com/o/video_idea_1.mp4?alt=media&token=52175d74-6ebc-47d6-ad92-cd3c447604fe".toUri(),
                "https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg".toUri()
            )
        )
        
        val recyclerLatest = view.findViewById(R.id.recycler_home_latest) as RecyclerView
        val recyclerPopular = view.findViewById(R.id.recycler_home_popular) as RecyclerView
        
        recyclerLatest.apply {
            this.adapter = LatestVideoAdapter(latestVideos, context)
        }
        recyclerPopular.apply {
            this.adapter = PopularVideoAdapter(popularVideos, context)
        }
        
        return view
    }
    
}