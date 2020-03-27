package com.jhm.android.pokusme.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.data.VoteData
import com.jhm.android.pokusme.global.TimeAgo
import kotlinx.android.synthetic.main.row_vote.view.*
import kotlinx.android.synthetic.main.row_vote_placeholder.view.*
import kotlinx.android.synthetic.main.row_vote_special.view.*


class VoteSpecialAdapter(private val votes: ArrayList<VoteData>) : RecyclerView.Adapter<VoteSpecialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (votes.size == 0) ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_vote_special_placeholder, parent, false))
        else ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_vote_special, parent, false))
    }

    override fun getItemCount(): Int {
        return if (votes.size == 0) 3
        else votes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (votes.size == 0) return

        val title = votes[position].title
        val content = votes[position].content

        val userId = votes[position].userId
        val voteId = votes[position].voteId

        val good = votes[position].goodCount
        val bad = votes[position].badCount

        holder.view.text_voteSpecial_title.text = title
        holder.view.text_voteSpecial_content.text = content
        holder.view.text_voteSpecial_score.text = (good.toInt() - bad.toInt()).toString()

        Glide.with(holder.view.context)
            .load("https://cdn.pixabay.com/photo/2014/12/29/08/29/lens-582605_960_720.jpg")
            .circleCrop()
            .placeholder(R.drawable.ic_default)
            .into(holder.view.image_voteSpecial_default)

        holder.view.setOnClickListener {
            Log.d("jhmlog", "title ${holder.view.text_vote_title.height}")
            Log.d("jhmlog", "name ${holder.view.text_vote_displayName.height}")
            Log.d("jhmlog", "default ${holder.view.image_vote_default.height}")
            Log.d("jhmlog", "content ${holder.view.text_vote_content.height}")
            Log.d("jhmlog", "default ${holder.view.text_vote_uploadTime.height}")
        }

        holder.view.button_voteSpecial_good.setOnClickListener {
            holder.view.text_vote_score.text = (good.toInt() + 1 - bad.toInt()).toString()
            transactScore(voteId, "good",
                onSuccess = { good, bad ->
                    holder.view.text_vote_score.text = (good.toInt() - bad.toInt()).toString()
                },
                onFailure = {
                    holder.view.text_vote_score.text = (good.toInt() - bad.toInt()).toString()
                }
            )
        }
        holder.view.button_voteSpecial_bad.setOnClickListener {
            holder.view.text_vote_score.text = (good.toInt() - 1 - bad.toInt()).toString()
            transactScore(voteId, "bad",
                onSuccess = { good, bad ->
                    holder.view.text_vote_score.text = (good.toInt() - bad.toInt()).toString()
                },
                onFailure = {
                    holder.view.text_vote_score.text = (good.toInt() - bad.toInt()).toString()
                }
            )
        }
    }

    private fun transactScore(voteId: String, which: String, onSuccess: (Number, Number) -> Unit, onFailure: () -> Unit) {
        val database = Firebase.firestore
        val documentReference = database.collection("VOTE").document(voteId)
        database.runTransaction { transaction ->
            val snapshot = transaction.get(documentReference)

            val newScore = snapshot.get(which) as Long + 1
            transaction.update(documentReference, which, newScore)

            null
        }
            .addOnSuccessListener {
                Log.d("jhmlog", "Transaction success")
                getVote(voteId) { good, bad -> onSuccess(good, bad) }
            }
            .addOnFailureListener {
                Log.d("jhmlog", "Transaction failure: $it")
                onFailure()
            }
    }

    private fun getVote(voteId: String, onSuccess: (Number, Number) -> Unit) {
        val database = Firebase.firestore
        database.collection("VOTE").document(voteId)
            .get()
            .addOnSuccessListener {
                onSuccess(it["good"] as Number, it["bad"] as Number)
            }
            .addOnFailureListener {

            }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
