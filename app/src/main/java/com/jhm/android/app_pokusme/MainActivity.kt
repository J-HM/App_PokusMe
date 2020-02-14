package com.jhm.android.app_pokusme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jhm.android.app_pokusme.data.UserData
import com.jhm.android.app_pokusme.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var currentUser: UserData
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val navigationController = findNavController(R.id.navigation_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_submit, R.id.navigation_profile)
        )
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        navigation_view.setupWithNavController(navigationController)
        
        auth = FirebaseAuth.getInstance()
    }
    
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d("jhmlog", "로그인 안됨")
            startLoginActivity()
        } else {
            Log.d("jhmlog", "로그인 됨")
            updateUserData(currentUser)
        }
    }
    
    private fun updateUserData(_currentUser: FirebaseUser?) {
        val displayName = _currentUser?.displayName.toString()
        val email = _currentUser?.email.toString()
        val isEmailVerified = _currentUser?.isEmailVerified
        this.currentUser = UserData(displayName, email, isEmailVerified)
    }
    
    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
