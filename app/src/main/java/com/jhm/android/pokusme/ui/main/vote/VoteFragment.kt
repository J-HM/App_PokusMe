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
            onSuccess = { assignVote(it) },
            onFailure = { handleError(it) }
        )
        
        return view
    }
    
    private fun assignVote(snapshot: QuerySnapshot) {
        for (document in snapshot) {
            Log.d("jhmlog", "${document.id} => ${document.data}")
            votes.add(
                VoteData(
                    document.data["title"] as String,
                    document.data["content"] as String,
                    document.data["time"] as Timestamp,
                    document.data["id"] as String
                )
            )
        }
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