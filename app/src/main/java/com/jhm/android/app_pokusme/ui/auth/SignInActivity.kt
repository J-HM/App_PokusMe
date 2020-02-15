package com.jhm.android.app_pokusme.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.jhm.android.app_pokusme.R
import com.jhm.android.app_pokusme.global.DialogAlert
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var isEmailValid: Boolean = false
    private var isPasswordValid: Boolean = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        auth = FirebaseAuth.getInstance()
        
        button_signIn_signIn.isEnabled = false
        button_signIn_signIn.setOnClickListener {
            signInWithNormal { finish() }
        }
        
        edit_signIn_email.addTextChangedListener {
            isEmailValid = validateEmail(it.toString())
            button_signIn_signIn.isEnabled = isEmailValid && isPasswordValid
        }
        edit_signIn_password.addTextChangedListener {
            isPasswordValid = validatePassword(it.toString())
            button_signIn_signIn.isEnabled = isEmailValid && isPasswordValid
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
    
    
    private fun validateEmail(email: String): Boolean {
        var isValid = true
        
        when {
            email.isBlank() -> {
                textInputLayout_signIn_email.error = "입력해주세요."
                isValid = false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                textInputLayout_signIn_email.error = "이메일을 입력해주세요."
                isValid = false
            }
            else -> textInputLayout_signIn_email.error = ""
        }
        
        return isValid
    }
    
    private fun validatePassword(password: String): Boolean {
        var isValid = true
        
        when {
            password.isBlank() -> {
                textInputLayout_signIn_password.error = "입력해주세요."
                isValid = false
            }
            else -> textInputLayout_signIn_password.error = ""
        }
        
        return isValid
    }
    
    
    private fun signInWithNormal(onSuccess: () -> Unit) {
        val email = edit_signIn_email.text.toString()
        val password = edit_signIn_password.text.toString()
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("jhmlog", "Sing In User With Email: SUCCESS")
                onSuccess()
            }
            .addOnFailureListener {
                Log.w("jhmlog", "Sing In User With Email: FAILURE", it)
                when (it) {
                    is FirebaseAuthInvalidUserException -> {
                        DialogAlert.showConfirmDialogAlert(
                            this, getString(R.string.userMessage_failure), "올바르지 않은 이메일 또는 비밀번호"
                        )
                    }
                    is FirebaseNetworkException -> {
                        DialogAlert.showConfirmDialogAlert(
                            this, getString(R.string.userMessage_failure), "네트워크 연결 실패"
                        )
                    }
                    else -> {
                        DialogAlert.showConfirmDialogAlert(
                            this, getString(R.string.userMessage_failure), "정의 되지 않은 오류"
                        )
                    }
                }
            }
    }

}