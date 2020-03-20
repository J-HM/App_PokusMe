package com.jhm.android.pokusme.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.VoteData
import com.jhm.android.pokusme.global.TimeAgo
import kotlinx.android.synthetic.main.row_vote.view.*


class VoteAdapter(private val votes: ArrayList<VoteData>) :
    RecyclerView.Adapter<VoteAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_vote, parent, false))
    }
    
    override fun getItemCount(): Int = votes.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = votes[position].title
        val content = votes[position].content
        val uploadTime = votes[position].uploadTime
        val displayName = votes[position].displayName
        
        holder.view.text_vote_title.text = title
        holder.view.text_vote_content.text = content
        holder.view.text_vote_uploadTime.text = TimeAgo.getTimeAgo(uploadTime)
        holder.view.text_vote_displayName.text = displayName
    }
    
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
