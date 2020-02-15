package com.jhm.android.app_pokusme.ui.auth

import android.content.res.ColorStateList
import android.graphics.Color
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
    private var isDisplayNameValid: Boolean = false
    private var isEmailValid: Boolean = false
    private var isPasswordValid: Boolean = false
    private var isPasswordConfirmValid: Boolean = false
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        auth = FirebaseAuth.getInstance()
        
        button_signUp_signUp.isEnabled = false
        button_signUp_signUp.setOnClickListener {
            signUp {
                DialogAlert.showConfirmDialogAlert(this, "성공", "가입이 완료 됐습니다.") {
                    finish()
                }
            }
        }
        
        edit_signUp_displayName.addTextChangedListener {
            isDisplayNameValid = validateDisplayName(it.toString())
            button_signUp_signUp.isEnabled = validateForm()
        }
        edit_signUp_email.addTextChangedListener {
            isEmailValid = validateEmail(it.toString())
            button_signUp_signUp.isEnabled = validateForm()
        }
        edit_signUp_password.addTextChangedListener {
            isPasswordValid = validatePassword(it.toString())
            if (isPasswordValid && !edit_signUp_passwordConfirm.text.toString().isBlank())
                isPasswordConfirmValid = validatePasswordConfirm(it.toString())
            button_signUp_signUp.isEnabled = validateForm()
        }
        edit_signUp_passwordConfirm.addTextChangedListener {
            isPasswordConfirmValid = validatePasswordConfirm(it.toString())
            button_signUp_signUp.isEnabled = validateForm()
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
    
    
    private fun validateForm(): Boolean {
        return isDisplayNameValid && isEmailValid && isPasswordValid && isPasswordConfirmValid
    }
    
    private fun signUp(onSuccess: () -> Unit) {
        val displayName = edit_signUp_displayName.text.toString()
        val email = edit_signUp_email.text.toString()
        val password = edit_signUp_password.text.toString()
        
        createUserWithEmail(email, password) {
            updateProfile(displayName) { onSuccess() }
        }
    }
    
    
    private fun validateDisplayName(displayName: String): Boolean {
        var isValid = true
        
        when {
            displayName.isBlank() -> {
                textInputLayout_signUp_displayName.error = "입력해주세요."
                isValid = false
            }
            displayName.length < 2 -> {
                textInputLayout_signUp_displayName.error = "2자 이상으로 설정해주세요."
                isValid = false
            }
            displayName.length > 10 -> {
                textInputLayout_signUp_displayName.error = "10자 이하로 설정해주세요."
                isValid = false
            }
            else -> textInputLayout_signUp_displayName.error = ""
            
        }
        
        return isValid
    }
    
    private fun validateEmail(email: String): Boolean {
        var isValid = true
    
        when {
            email.isBlank() -> {
                textInputLayout_signUp_email.error = "입력해주세요."
                isValid = false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                textInputLayout_signUp_email.error = "이메일을 입력해주세요."
                isValid = false
            }
            else -> textInputLayout_signUp_email.error = ""
        }
        
        return isValid
    }
    
    private fun validatePassword(password: String): Boolean {
        var isValid = true
        
        when {
            password.isBlank() -> {
                textInputLayout_signUp_password.error = "입력해주세요."
                isValid = false
            }
            password.length < 8 -> {
                textInputLayout_signUp_password.error = "8자 이상 입력해주세요."
                isValid = false
            }
            else -> {
                textInputLayout_signUp_password.error = ""
            }
        }
        
        return isValid
    }
    
    private fun validatePasswordConfirm(passwordConfirm: String): Boolean {
        var isValid = true
        
        when {
            passwordConfirm.isBlank() -> {
                textInputLayout_signUp_passwordConfirm.error = "입력해주세요."
                isValid = false
            }
            else -> textInputLayout_signUp_passwordConfirm.error = ""
        }
        
        if (isPasswordValid && passwordConfirm == edit_signUp_password.text.toString()) {
            textInputLayout_signUp_passwordConfirm.setStartIconTintList(ColorStateList.valueOf(Color.BLUE))
            textInputLayout_signUp_passwordConfirm.error = ""
        } else {
            textInputLayout_signUp_passwordConfirm.setStartIconTintList(ColorStateList.valueOf(Color.RED))
            isValid = false
        }
        
        return isValid
    }
    
    
    private fun createUserWithEmail(email: String, password: String, onSuccess: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(this) {
                Log.d("jhmlog", "Create User With Email: SUCCESS")
                onSuccess()
            }
            .addOnFailureListener {
                Log.w("jhmlog", "Create User With Email: FAILURE", it)
                DialogAlert.showConfirmDialogAlert(this, getString(R.string.userMessage_failure), "${it.message}")
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