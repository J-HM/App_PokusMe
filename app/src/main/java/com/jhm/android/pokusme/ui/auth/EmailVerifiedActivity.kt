package com.jhm.android.pokusme.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jhm.android.pokusme.MainActivity
import com.jhm.android.pokusme.R
import com.jhm.android.pokusme.global.DialogAlert
import kotlinx.android.synthetic.main.activity_email_verified.*

class EmailVerifiedActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verified)
        
        text_emailVerified_email.text = intent.extras?.getString("email")
        
        button_emailVerified_signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        
        button_emailVerified_refresh.setOnClickListener {
            checkUserIsEmailVerified()
        }
    
        button_emailVerified_start.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        
        button_emailVerified_start.visibility = View.GONE
        
        auth = FirebaseAuth.getInstance()
        sendEmailVerification()
    }
    
    override fun onStart() {
        super.onStart()
        checkUserIsEmailVerified()
    }
    
    private fun checkUserIsEmailVerified() {
        auth.currentUser?.reload()
            ?.addOnSuccessListener {
                auth.currentUser?.let {
                    if (it.isEmailVerified) {
                        Log.d("jhmlog", "Email Verified Success")
                        image_emailVerified_isCheck.setColorFilter(Color.parseColor("#FF00C853"))
                        button_emailVerified_refresh.visibility = View.GONE
                        button_emailVerified_signOut.visibility = View.GONE
                        button_emailVerified_start.visibility = View.VISIBLE
                        text_emailVerified_message.visibility = View.GONE
                        text_emailVerified_title.text = getString(R.string.emailVerified_complete)
                    }
                }
            }
            ?.addOnFailureListener {
                DialogAlert.showPositiveDialogAlert(this, getString(R.string.userMessage_failure), it.message.toString())
            }
    }
    
    private fun sendEmailVerification() {
        val user = auth.currentUser
        
        user?.sendEmailVerification()
            ?.addOnSuccessListener {
                Log.d("jhmlog", "Email sent succeed.")
            }
            ?.addOnFailureListener {
                Log.d("jhmlog", "Email sent failed.")
            }
    }
}
