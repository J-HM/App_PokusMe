package com.jhm.android.pokusme.ui.drawer.profile

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jhm.android.pokusme.MainActivity
import com.jhm.android.pokusme.R
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        
        setSupportActionBar(toolbar_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        
        val displayName = intent.extras?.getString("displayName")
        val email = intent.extras?.getString("email")
        Log.d("jhmlog", "test2 $displayName $email")
        
        text_profile_displayName.text = displayName
        text_profile_email.text = email
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
}
