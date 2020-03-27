package com.jhm.android.pokusme.ui.main.vote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.adapter.VoteAdapter
import com.jhm.android.pokusme.adapter.VoteSpecialAdapter
import com.jhm.android.pokusme.data.VoteData
import kotlinx.android.synthetic.main.fragment_vote.*


class VoteFragment : Fragment() {
    private val database = Firebase.firestore
    private val votes = ArrayList<VoteData>()
    private val popularVotes = ArrayList<VoteData>()
    private val disputeVotes = ArrayList<VoteData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vote, container, false)
        
        view.findViewById<RecyclerView>(R.id.recycler_vote).apply { adapter = VoteAdapter(votes) }
        
        view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh_vote).apply {
            setOnRefreshListener {
                if (votes.size == 0 && popularVotes.size == 0 && disputeVotes.size == 0) {
                    swipeRefresh_vote.isRefreshing = false
                    return@setOnRefreshListener
                }
                votes.clear()
                popularVotes.clear()
                disputeVotes.clear()
                getVotes(votes) { swipeRefresh_vote.isRefreshing = false }
                getVotes(popularVotes) { swipeRefresh_vote.isRefreshing = false }
                getVotes(disputeVotes) { swipeRefresh_vote.isRefreshing = false }
            }
        }
        
        getVotes(votes) { recycler_vote.adapter = VoteAdapter(votes) }
        getVotes(popularVotes) { recycler_vote_popular.adapter = VoteSpecialAdapter(popularVotes) }
        getVotes(disputeVotes) { recycler_vote_dispute.adapter = VoteSpecialAdapter(disputeVotes) }
        
        return view
    }
    
    private fun getVotes(votes: ArrayList<VoteData>, onSuccess: (ArrayList<VoteData>) -> Unit) {
        database.collection("VOTE")
            .get()
            .addOnSuccessListener {
                assignVote(votes, it)
                onSuccess(votes)
            }
            .addOnFailureListener { handleError(it) }
    }
    
    private fun assignVote(votes: ArrayList<VoteData>, snapshot: QuerySnapshot) {
        Log.d("jhmlog", "GET VOTE SUCCESS: ${snapshot.size()}")
        snapshot.forEachIndexed { index, document ->
            Log.d("jhmlog", "$index -> ${document.data}")
            val title = document.data["title"] as String
            val content = document.data["content"] as String
            val uploadTime = document.data["time"] as Timestamp
            val userId = document.data["id"] as String
            val voteId = document.id
            val commentCount = document.data["comment"] as Number
            val goodCount = document.data["good"] as Number
            val badCount = document.data["bad"] as Number
            
            votes.add(VoteData(title, content, uploadTime, userId, voteId, commentCount, goodCount, badCount))
            assignName(index, userId)
        }
    }
    
    private fun assignName(index: Int, userId: String) {
        getDisplayName(userId) {
            votes[index].displayName = it
            Log.d("jhmlog", "embed name $index $it")
            recycler_vote.adapter?.notifyItemChanged(index)
        }
    }
    
    private fun getDisplayName(userId: String, onSuccess: (String) -> Unit) {
        val documentReference = database.collection("USER").document(userId)
        documentReference.get().addOnSuccessListener { onSuccess(it["name"] as String) }
    }
    
    private fun handleError(exception: Exception) {
        Log.d("jhmlog", "GET VOTE FAILURE: $exception")
    }
}