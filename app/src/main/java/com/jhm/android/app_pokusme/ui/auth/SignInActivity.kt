package com.jhm.android.app_pokusme.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jhm.android.app_pokusme.MainActivity
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        auth = FirebaseAuth.getInstance()
        
        button_signIn_signIn.setOnClickListener {
            signInWithNormal(edit_signIn_email.text.toString(), edit_signIn_password.text.toString())
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun validateForm(email: String, password: String): Boolean {
        var valid = true
        
        if (email.isBlank()) {
            edit_signIn_email.error = "입력해주세요."
            valid = false
        } else edit_signIn_email.error = null
        
        if (password.isBlank()) {
            edit_signIn_password.error = "입력해주세요."
            valid = false
        } else edit_signIn_password.error = null
        
        return valid
    }
    
    private fun signInWithNormal(email: String, password: String) {
        if (!validateForm(email, password)) return
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                    startMainActivity()
                else
                    Toast.makeText(baseContext, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    
}
