package com.gitzblitz.mychatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gitzblitz.mychatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  var mAuth: FirebaseAuth? = null
  var user: FirebaseUser? = null
  var mAuthListener: FirebaseAuth.AuthStateListener? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mAuth = FirebaseAuth.getInstance()

    mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth: FirebaseAuth ->
      user = firebaseAuth.currentUser

      if (user != null) {
        // go to dashboard
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
      } else {
        Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show()
      }
    }

    btnCreateAccount.setOnClickListener {
      startActivity(Intent(this, CreateAccountActivity::class.java))
    }

    btnLogin.setOnClickListener {
      startActivity(Intent(this, LoginActivity::class.java))
    }
  }

  override fun onStart() {
    super.onStart()
    mAuth!!.addAuthStateListener(mAuthListener!!)
  }

  override fun onStop() {
    super.onStop()
    if (mAuthListener != null) {
      mAuth!!.removeAuthStateListener(mAuthListener!!)
    }
  }
}
