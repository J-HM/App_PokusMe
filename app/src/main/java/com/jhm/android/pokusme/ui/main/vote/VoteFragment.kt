package com.jhm.android.pokusme.ui.main.vote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.adapter.VoteAdapter
import com.jhm.android.pokusme.data.VoteData


class VoteFragment : Fragment() {
    private val votes = ArrayList<VoteData>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vote, container, false)
        
        (view.findViewById(R.id.recycler_vote) as RecyclerView).apply {
            this.adapter = VoteAdapter(votes)
        }
        
        getVote(
            onSuccess = {
                Log.d("jhmlog", "GET VOTE SUCCESS: $it")
                assignVote(it)
                assignName()
            },
            onFailure = {
                Log.d("jhmlog", "GET VOTE FAILURE: $it")
                handleError(it)
            }
        )
        
        return view
    }
    
    private fun assignVote(snapshot: QuerySnapshot) {
        Log.d("jhmlog", "${snapshot.size()}")
        for (document in snapshot) {
            Log.d("jhmlog", "${document.id} => ${document.data}")
            votes.add(
                VoteData(
                    title = document.data["title"] as String,
                    content = document.data["content"] as String,
                    uploadTime = document.data["time"] as Timestamp,
                    userId = document.data["id"] as String
                )
            )
        }
    }
    
    private fun assignName() {
        for (index in votes.indices)
            votes[index].displayName = getDisplayName(votes[index].userId)
    }
    
    private fun getDisplayName(userId: String?): String? {
        var displayName: String? = null
        
        val database = FirebaseFirestore.getInstance()
        val documentReference = userId?.let { database.collection("USER").document(it) }
        documentReference?.get()?.addOnSuccessListener { document ->
            document?.let {
                Log.d("jhmlog", "GET NAME SUCCESS: ${document.data}")
                displayName = document.data?.get("name") as String
            }
        }?.addOnFailureListener { exception ->
            Log.d("jhmlog", "GET NAME FAILURE: ", exception)
        }
        
        return displayName
    }
    
    private fun handleError(exception: Exception) {
    }
    
    private fun getVote(onSuccess: (QuerySnapshot) -> Unit, onFailure: (Exception) -> Unit) {
        val database = FirebaseFirestore.getInstance()
        
        database.collection("VOTE")
//            .orderBy("upload time")
            .get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
        
    }
    
}