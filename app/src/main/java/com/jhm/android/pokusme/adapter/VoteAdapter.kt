package com.jhm.android.pokusme.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.VoteData
import com.jhm.android.pokusme.global.TimeAgo
import kotlinx.android.synthetic.main.row_vote.view.*


class VoteAdapter(private val votes: ArrayList<VoteData>) : RecyclerView.Adapter<VoteAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (votes.size == 0) ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_vote_placeholder, parent, false))
        else ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_vote, parent, false))
    }
    
    override fun getItemCount(): Int {
        return if (votes.size == 0) 3
        else votes.size
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (votes.size == 0) return
        val title = votes[position].title
        val content = votes[position].content
        val uploadTime = votes[position].uploadTime
        val displayName = votes[position].displayName
        var good = votes[position].good
        var bad = votes[position].bad
        Log.d("jhmlog", "$good $bad")
        if (good == null) good = 0
        if (bad == null) bad = 0
        
        holder.view.text_vote_title.text = title
        holder.view.text_vote_content.text = content
        holder.view.text_vote_uploadTime.text = TimeAgo.getTimeAgo(uploadTime)
        holder.view.text_vote_displayName.text = displayName
        holder.view.text_vote_score.text = (good.toInt() - bad.toInt()).toString()
        
        holder.view.setOnClickListener {
            Log.d("jhmlog", "title ${holder.view.text_vote_title.height}")
            Log.d("jhmlog", "name ${holder.view.text_vote_displayName.height}")
            Log.d("jhmlog", "default ${holder.view.image_vote_default.height}")
            Log.d("jhmlog", "content ${holder.view.text_vote_content.height}")
            Log.d("jhmlog", "default ${holder.view.text_vote_uploadTime.height}")
        }
    
        holder.view.button_vote_bad.setOnClickListener {
        
        }
        
        holder.view.button_vote_good.setOnClickListener {
        
        }
    }
    
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
