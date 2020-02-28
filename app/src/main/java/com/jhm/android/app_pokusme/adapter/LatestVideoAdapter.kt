package com.jhm.android.app_pokusme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.app_pokusme.R
import com.jhm.android.app_pokusme.data.VideoData
import kotlinx.android.synthetic.main.row_latest_video.view.*


class LatestVideoAdapter(private val latestVideos: ArrayList<VideoData>) : RecyclerView.Adapter<LatestVideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_latest_video, parent, false))
    }
    
    override fun getItemCount(): Int = latestVideos.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = latestVideos[position].title
        val id = latestVideos[position].id
        
        holder.view.text_latestVideo_title.text = title
        // getLifecycle().addObserver(holder.view.youtube_player_view)
    }
    
    
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
