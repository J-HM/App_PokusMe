package com.jhm.android.app_pokusme.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.jhm.android.app_pokusme.MainActivity
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        auth = FirebaseAuth.getInstance()
        
        button_login_signIn.setOnClickListener(this)
        button_login_facebook.setOnClickListener(this)
        button_login_google.setOnClickListener(this)
        button_login_kakao.setOnClickListener(this)
        button_login_naver.setOnClickListener(this)
    }
    
    public override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            startMainActivity()
        }
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_login_signIn -> signInWithNormal(edit_login_email.text.toString(), edit_login_password.text.toString())
            R.id.button_login_facebook -> signInWithGoogle()
            R.id.button_login_google -> signInWithFacebook()
            R.id.button_login_kakao -> signInWithKakao()
            R.id.button_login_naver -> signInWithNaver()
        }
    }
    
    private fun signInWithNormal(email: String, password: String) {
        Log.d("jhmlog", "signIn:$email")
        if (!validateForm())
            return
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("jhmlog", "signInWithEmail:success")
                    startMainActivity()
                } else {
                    Log.w("jhmlog", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    
    private fun signInWithGoogle() {
    }
    
    private fun signInWithFacebook() {
    }
    
    private fun signInWithKakao() {
    }
    
    private fun signInWithNaver() {
    }
    
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    
    private fun validateForm(): Boolean {
        var valid = true
        
        val email = edit_login_email.text.toString()
        if (TextUtils.isEmpty(email)) {
            edit_login_email.error = "Required."
            valid = false
        } else {
            edit_login_email.error = null
        }
        
        val password = edit_login_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            edit_login_password.error = "Required."
            valid = false
        } else {
            edit_login_password.error = null
        }
        
        return valid
    }
    
}
