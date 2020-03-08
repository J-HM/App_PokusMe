package com.jhm.android.pokusme

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jhm.android.pokusme.data.UserData
import com.jhm.android.pokusme.ui.auth.EmailVerifiedActivity
import com.jhm.android.pokusme.ui.auth.LoginActivity
import com.jhm.android.pokusme.ui.drawer.NavigationFragment
import com.jhm.android.pokusme.ui.home.HomeFragment
import com.jhm.android.pokusme.ui.submit.SubmitFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var fragmentManager: FragmentManager? = null
    
    private lateinit var homeFragment: HomeFragment
    private lateinit var submitFragment: SubmitFragment
    
    private val navigationFragment = NavigationFragment()
    
    var currentUser: UserData? = null
    private var isHome: Boolean = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        
        setOnFABClickListener(
            setHomeOnClickListener = { homeOnClickListener() },
            setSubmitOnClickListener = { submitOnClickListener() }
        )
        
        bottomAppbar_main.setNavigationOnClickListener {
            this.navigationFragment.show(supportFragmentManager, "TAG")
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
    
    private fun setOnFABClickListener(setHomeOnClickListener: () -> Unit, setSubmitOnClickListener: () -> Unit) {
        fragmentManager = supportFragmentManager
        submitFragment = SubmitFragment()
        homeFragment = HomeFragment()
        fragmentManager!!.beginTransaction().add(R.id.frame_main, submitFragment).commit()
        fragmentManager!!.beginTransaction().add(R.id.frame_main, homeFragment).commit()
        button_main_core.setOnClickListener {
            if (isHome) setSubmitOnClickListener()
            else setHomeOnClickListener()
        }
    }
    
    private fun submitOnClickListener() {
        button_main_core.animate().setDuration(300).rotationBy(-360f).start() // 성능저하
        fragmentManager?.beginTransaction()?.show(submitFragment)?.commit()
        fragmentManager?.beginTransaction()?.hide(homeFragment)?.commit()
        bottomAppbar_main.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER // 성능저하
        button_main_core.setImageResource(R.drawable.ic_home)
        tabbar_main.visibility = View.GONE
        appbar_main.setExpanded(true)
        isHome = false
    }
    
    private fun homeOnClickListener() {
        button_main_core.animate().setDuration(300).rotationBy(360f).start() // 성능저하
        fragmentManager?.beginTransaction()?.show(homeFragment)?.commit()
        fragmentManager?.beginTransaction()?.hide(submitFragment)?.commit()
        bottomAppbar_main.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END // 성능저하
        button_main_core.setImageResource(R.drawable.ic_brush)
        tabbar_main.visibility = View.VISIBLE
        isHome = true
    }
    
    private fun updateUserData(_currentUser: FirebaseUser?) {
        val displayName = _currentUser?.displayName.toString()
        val email = _currentUser?.email.toString()
        val isEmailVerified = _currentUser?.isEmailVerified
        this.currentUser = UserData(displayName, email, isEmailVerified)
    }
    
}