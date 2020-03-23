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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.adapter.VoteAdapter
import com.jhm.android.pokusme.data.VoteData
import kotlinx.android.synthetic.main.fragment_vote.*
import kotlinx.coroutines.runBlocking


class VoteFragment : Fragment() {
    private val votes = ArrayList<VoteData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vote, container, false)
        
        view.findViewById<RecyclerView>(R.id.recycler_vote).apply { adapter = VoteAdapter(votes) }
        
        view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh_vote).apply {
            setOnRefreshListener {
                if (votes.size == 0) {
                    swipeRefresh_vote.isRefreshing = false
                    return@setOnRefreshListener
                }
                votes.clear()
                getVote {
                    recycler_vote.adapter?.notifyDataSetChanged()
                    swipeRefresh_vote.isRefreshing = false
                }
            }
        }
        
        getVote { recycler_vote.adapter = VoteAdapter(votes) }
        
        return view
    }
    
    private fun getVote(onSuccess: (QuerySnapshot) -> Unit) {
        val database = FirebaseFirestore.getInstance()
        database.collection("VOTE")
            .get()
            .addOnSuccessListener {
                assignVote(it) { onSuccess(it) }
            }
            .addOnFailureListener { handleError(it) }
    }

    private fun assignVote(snapshot: QuerySnapshot, onSuccess: () -> Unit) {
        Log.d("jhmlog", "GET VOTE SUCCESS: ${snapshot.size()}")
        snapshot.forEachIndexed { index, document ->
            val title = document.data["title"] as String
            val content = document.data["content"] as String
            val uploadTime = document.data["time"] as Timestamp
            val userId = document.data["id"] as String
            val voteId = document.id
            val commentCount = document.data["comment"] as Number
            val goodCount = document.data["good"] as Number
            val badCount = document.data["bad"] as Number

            votes.add(VoteData(title, content, uploadTime, userId, voteId, commentCount, goodCount, badCount))

            getDisplayName(userId) { displayName ->
                votes[index].displayName = displayName
                Log.d("jhmlog", "embed name $index")
                if ((index + 1) == snapshot.size()) {
                    onSuccess()
                }
            }
        }
        Log.d("jhmlog", "out of loop")
    }

    private fun getDisplayName(userId: String?, onSuccess: (String) -> Unit) {
        val database = FirebaseFirestore.getInstance()

        if (userId == null) {
            onSuccess("이름없음")
            return
        }

        database.collection("USER").document(userId)
            .get()
            .addOnSuccessListener { onSuccess(it.data?.get("name") as String) }
            .addOnFailureListener { Log.d("jhmlog", "GET NAME FAILURE: ", it) }
    }
    
    private fun handleError(exception: Exception) {
        Log.d("jhmlog", "GET VOTE FAILURE: $exception")
    }
}