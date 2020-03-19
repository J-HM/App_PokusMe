package com.jhm.android.pokusme.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.VoteData
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
        
        holder.view.text_vote_title.text = title
        Log.d("jhmlog", "test $content")
    }
    
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
