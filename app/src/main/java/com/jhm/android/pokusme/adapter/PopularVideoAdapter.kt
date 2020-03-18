package com.jhm.android.pokusme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.VideoData
import kotlinx.android.synthetic.main.row_popular_video.view.*


class PopularVideoAdapter(private val popularVideos: ArrayList<VideoData>, val context: Context) :
    RecyclerView.Adapter<PopularVideoAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_popular_video, parent, false))
    }
    
    override fun getItemCount(): Int = popularVideos.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = popularVideos[position].title
        val videoUri = popularVideos[position].videoUri
        
        holder.view.text_popularVideo_title.text = title
        
        val player = SimpleExoPlayer.Builder(context).build()
        holder.view.video_popularVideo.player = player
        
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, Util.getUserAgent(context, R.string.app_name.toString()))
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri)
        player.prepare(videoSource)
        
    }
    
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
