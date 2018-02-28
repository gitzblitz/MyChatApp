package com.gitzblitz.mychatapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gitzblitz.mychatapp.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

  var mDatabase: DatabaseReference? = null
  var mCurrentUser: FirebaseUser? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_status)

    supportActionBar!!.title = "Edit Status"

    if (intent.extras != null){
      var oldStatus = intent.extras.get("status")

      statusUpdateEditText.setText(oldStatus.toString())
    }
    if (intent.extras == null){
      statusUpdateEditText.setText("Enter your new Status")
    }

    statusUpdateBtn.setOnClickListener {

    }
  }
}
