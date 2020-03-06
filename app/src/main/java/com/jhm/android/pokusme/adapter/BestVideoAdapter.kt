package com.jhm.android.pokusme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.VideoData
import kotlinx.android.synthetic.main.row_best_video.view.*

class BestVideoAdapter(private val bestVideos: ArrayList<VideoData>) : RecyclerView.Adapter<BestVideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_best_video, parent, false))
    }
    
    override fun getItemCount(): Int = bestVideos.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = bestVideos[position].title
        holder.view.text_bestVideo_title.text = title
    }
    
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
