package com.gitzblitz.mychatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gitzblitz.mychatapp.BuildConfig
import com.gitzblitz.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  val PARAMETER_WELCOME_TEXT: String = "welcome_text"
  var mAuth: FirebaseAuth? = null
  var user: FirebaseUser? = null
  var mAuthListener: FirebaseAuth.AuthStateListener? = null
  var mRemoteConfig: FirebaseRemoteConfig? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mAuth = FirebaseAuth.getInstance()
    mRemoteConfig = FirebaseRemoteConfig.getInstance()
    mRemoteConfig!!.setDefaults(R.xml.remote_config_defaults)

    var firebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build()

    mRemoteConfig!!.setConfigSettings(firebaseRemoteConfigSettings)




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
    fetchWelcome()
  }

  fun fetchWelcome(): Unit {
//
    var cacheExpiration: Long = 3600

    if (mRemoteConfig!!.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0
    }

    mRemoteConfig!!.fetch(cacheExpiration)
        .addOnCompleteListener {

          task: Task<Void> ->
          //
          if (task.isSuccessful) {
            Toast.makeText(this, "Fetch Succeeded", Toast.LENGTH_LONG).show()
            mRemoteConfig!!.activateFetched()

          } else {
            Toast.makeText(this, "Fetch Failed", Toast.LENGTH_LONG).show()
          }
          showWelcome()

        }


  }

  private fun showWelcome() {
    var configText = mRemoteConfig!!.getString(PARAMETER_WELCOME_TEXT)

    Toast.makeText(this, configText, Toast.LENGTH_SHORT).show()
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
