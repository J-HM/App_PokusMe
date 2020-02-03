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
import com.jhm.android.app_pokusme.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    
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
        updateUI(currentUser)
    }
    
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d("jhmlog", user.toString())
        } else {
            val nextIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextIntent)
            finish()
            Log.d("jhmlog", "null")
        }
    }
}
