package com.jhm.android.app_pokusme.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.jhm.android.app_pokusme.R
import com.jhm.android.app_pokusme.global.DialogAlert
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        auth = FirebaseAuth.getInstance()
        
        button_signUp_signUp.setOnClickListener { signUp() }
        edit_signUp_displayName.addTextChangedListener {
        
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
    
    private fun signUp() {
        val displayName = edit_signUp_displayName.text.toString()
        val email = edit_signUp_email.text.toString()
        val password = edit_signUp_password.text.toString()
        val passwordConfirm = edit_signUp_passwordConfirm.text.toString()
        
        if (!validateForm(displayName, email, password, passwordConfirm)) return
        
        createUserWithEmail(email, password) {
            updateProfile(displayName) {
                // 완료
                Log.d("jhmlog", "User profile updated.")
            }
        }
    }
    
    private fun validateForm(displayName: String, email: String, password: String, passwordConfirm: String): Boolean {
        var valid = true
        
        if (displayName.isBlank()) {
            edit_signUp_displayName.error = "입력해주세요."
            valid = false
        } else edit_signUp_displayName.error = null
        
        if (email.isBlank()) {
            edit_signUp_email.error = "입력해주세요."
            valid = false
        } else edit_signUp_email.error = null
        
        if (password.isBlank()) {
            edit_signUp_password.error = "입력해주세요."
            valid = false
        } else edit_signUp_password.error = null
        
        if (passwordConfirm.isBlank()) {
            edit_signUp_passwordConfirm.error = "입력해주세요."
            valid = false
        } else edit_signUp_passwordConfirm.error = null
        
        return valid
    }
    
    private fun createUserWithEmail(email: String, password: String, onSuccess: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("jhmlog", "Create User With Email: SUCCESS")
                    onSuccess()
                } else {
                    DialogAlert.showConfirmDialogAlert(this, "실패", "가입에 문제가 생겼습니다.")
                    Log.w("jhmlog", "Create User With Email: FAILURE", task.exception)
                }
            }
    }
    
    private fun updateProfile(displayName: String, onSuccess: () -> Unit) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { if (it.isSuccessful) onSuccess() }
    }
}