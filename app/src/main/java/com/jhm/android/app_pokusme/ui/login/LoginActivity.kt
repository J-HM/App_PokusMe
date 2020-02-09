package com.jhm.android.app_pokusme.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jhm.android.app_pokusme.MainActivity
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        
        button_login_signIn.setOnClickListener(this)
        button_login_facebook.setOnClickListener(this)
        button_login_google.setOnClickListener(this)
        button_login_kakao.setOnClickListener(this)
        button_login_naver.setOnClickListener(this)
    }
    
    override fun onStart() {
        super.onStart()
        auth.currentUser?.let { startMainActivity() }
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_login_signIn -> signInWithNormal(edit_login_email.text.toString(), edit_login_password.text.toString())
            R.id.button_login_facebook -> signInWithFacebook()
            R.id.button_login_google -> signInWithGoogle()
            R.id.button_login_kakao -> signInWithKakao()
            R.id.button_login_naver -> signInWithNaver()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("jhmlog", "Google sign in failed ${e.localizedMessage}", e)
            }
        }
    }
    
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("jhmlog", "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startMainActivity()
                    Log.d("jhmlog", "signInWithCredential:success")
                } else {
                    Log.w("jhmlog", "signInWithCredential:failure", task.exception)
                }
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
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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
    
    
    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
