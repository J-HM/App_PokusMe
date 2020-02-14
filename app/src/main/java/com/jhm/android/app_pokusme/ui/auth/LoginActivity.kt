package com.jhm.android.app_pokusme.ui.auth


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jhm.android.app_pokusme.MainActivity
import com.jhm.android.app_pokusme.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        callbackManager = CallbackManager.Factory.create()
        
        button_login_facebook.setOnClickListener(this)
        button_login_google.setOnClickListener(this)
        button_login_kakao.setOnClickListener(this)
        button_login_naver.setOnClickListener(this)
        
        button_login_signIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        
        button_login_signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
    
    override fun onStart() {
        super.onStart()
        auth.currentUser?.let { startMainActivity() }
    }
    
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_login_facebook -> signInWithFacebook()
            R.id.button_login_google -> signInWithGoogle()
            R.id.button_login_kakao -> signInWithKakao()
            R.id.button_login_naver -> signInWithNaver()
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                firebaseAuthWithCredential(credential)
            } catch (e: ApiException) {
                Log.w("jhmlog", "Google sign in failed ${e.message}", e)
            }
        }
    }
    
    private fun firebaseAuthWithCredential(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startMainActivity()
                    Log.d("jhmlog", "Sign In With Credential: SUCCESS")
                } else {
                    Log.w("jhmlog", "Sign In With Credential: FAILURE", task.exception)
                }
            }
    }
    
    private fun firebaseAuthWithCustomToken(customToken: String) {
        auth.signInWithCustomToken(customToken)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startMainActivity()
                    Log.d("jhmlog", "Sign In With Custom Token: SUCCESS")
                } else {
                    Log.w("jhmlog", "Sign In With Credential: FAILURE", task.exception)
                }
            }
    }
    
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    
    private fun signInWithFacebook() {
        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(this, listOf("public_profile", "email"))
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("jhmlog", "facebook:onSuccess:$loginResult")
                val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                firebaseAuthWithCredential(credential)
            }
            
            override fun onCancel() {
                Log.d("jhmlog", "facebook:onCancel")
            }
            
            override fun onError(error: FacebookException) {
                Log.d("jhmlog", "facebook:onError", error)
            }
            
        })
    }
    
    private fun signInWithKakao() {
        val token = ""
        firebaseAuthWithCustomToken(token)
    }
    
    private fun signInWithNaver() {
        val token = ""
        firebaseAuthWithCustomToken(token)
    }
    
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    
    
    companion object {
        private const val RC_SIGN_IN = 9001
    }
}