package com.jhm.android.app_pokusme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jhm.android.app_pokusme.data.UserData
import com.jhm.android.app_pokusme.ui.auth.EmailVerifiedActivity
import com.jhm.android.app_pokusme.ui.auth.LoginActivity
import com.jhm.android.app_pokusme.ui.home.HomeFragment
import com.jhm.android.app_pokusme.ui.submit.SubmitFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var fragmentManager: FragmentManager? = null
    
    private var homeFragment: HomeFragment? = null
    private var submitFragment: SubmitFragment? = null
    
    lateinit var currentUser: UserData
    var isHome: Boolean = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        auth = FirebaseAuth.getInstance()
        
        fragmentManager = supportFragmentManager
        homeFragment = HomeFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.frame_main, homeFragment!!)?.commit()
        
        button_main_core.setOnClickListener {
            if (isHome) {
                if (submitFragment == null) {
                    submitFragment = SubmitFragment()
                    fragmentManager!!.beginTransaction().add(R.id.frame_main, submitFragment!!).commit()
                }
                fragmentManager?.beginTransaction()?.show(submitFragment!!)?.commit()
                fragmentManager?.beginTransaction()?.hide(homeFragment!!)?.commit()
                bottomAppbar_main.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                button_main_core.setImageResource(R.drawable.ic_home)
                isHome = false
                
            } else {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    fragmentManager!!.beginTransaction().add(R.id.frame_main, homeFragment!!).commit()
                }
                fragmentManager?.beginTransaction()?.show(homeFragment!!)?.commit()
                fragmentManager?.beginTransaction()?.hide(submitFragment!!)?.commit()
                bottomAppbar_main.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                button_main_core.setImageResource(R.drawable.ic_brush)
                isHome = true
            }
        }
        
        bottomAppbar_main.setNavigationOnClickListener {
            Log.d("jhmlog", "test")
        }
    }
    
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        when {
            currentUser == null -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            !currentUser.isEmailVerified -> {
                Intent(this, EmailVerifiedActivity::class.java).apply {
                    putExtra("email", currentUser.email)
                    startActivity(this)
                }
                finish()
            }
            else -> updateUserData(currentUser)
        }
    }
    
    private fun updateUserData(_currentUser: FirebaseUser?) {
        val displayName = _currentUser?.displayName.toString()
        val email = _currentUser?.email.toString()
        val isEmailVerified = _currentUser?.isEmailVerified
        this.currentUser = UserData(displayName, email, isEmailVerified)
    }
}
