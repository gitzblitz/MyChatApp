package com.gitzblitz.mychatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gitzblitz.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

  var mDatabase: DatabaseReference? = null
  var mCurrentUser: FirebaseUser? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_status)

    supportActionBar!!.title = "Edit Status"

    if (intent.extras != null) {
      var oldStatus = intent.extras.get("status")

      statusUpdateEditText.setText(oldStatus.toString())
    }
    if (intent.extras == null) {
      statusUpdateEditText.setText("Enter your new Status")
    }

    statusUpdateBtn.setOnClickListener {

      mCurrentUser = FirebaseAuth.getInstance().currentUser
      var userID = mCurrentUser!!.uid

      mDatabase = FirebaseDatabase.getInstance().reference
          .child("Users")
          .child(userID)

      var status = statusUpdateEditText.text.toString().trim()

      mDatabase!!.child("status")
          .setValue(status).addOnCompleteListener { task: Task<Void> ->
        if (task.isSuccessful) {
          Toast.makeText(this, "Status updated successfully", Toast.LENGTH_SHORT).show()
          startActivity(Intent(this, SettingsActivity::class.java))
          finish()
        } else {
          Toast.makeText(this, "Status not updated", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
