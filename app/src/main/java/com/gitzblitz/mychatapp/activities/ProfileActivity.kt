package com.gitzblitz.mychatapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gitzblitz.mychatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

  var mDatabase: DatabaseReference? = null
  var mCurrentUser: FirebaseUser? = null
  var userId: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    supportActionBar!!.title = "Profile"
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    if (intent.extras != null){
      userId = intent.extras.get("userId").toString()

      mCurrentUser = FirebaseAuth.getInstance().currentUser
      mDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

      setupProfile()
    }
  }

  private fun setupProfile() {

    mDatabase!!.addValueEventListener(object: ValueEventListener{
      override fun onCancelled(databaseError: DatabaseError?) {

      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        var displayName = dataSnapshot.child("display_name").toString()
        var status = dataSnapshot.child("status").toString()
        var profileImage = dataSnapshot.child("image").toString()


      }
    })
  }
}
