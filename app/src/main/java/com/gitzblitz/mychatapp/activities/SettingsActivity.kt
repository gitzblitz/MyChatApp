package com.gitzblitz.mychatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gitzblitz.mychatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

  var mDatabase: DatabaseReference? = null
  var mCurrentUser: FirebaseUser? = null
  var mStorageReference: StorageReference? = null
  var GALLERY_ID: Int = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    supportActionBar!!.title = "Settings"

    //instantiate references


    mCurrentUser = FirebaseAuth.getInstance().currentUser

    var userID = mCurrentUser!!.uid
    mDatabase = FirebaseDatabase.getInstance().reference
        .child("Users")
        .child(userID)

    mDatabase!!.addValueEventListener(object : ValueEventListener {

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        var displayName = dataSnapshot.child("display_name").value
        var image = dataSnapshot.child("image").value
        var userStatus = dataSnapshot.child("status").value
        var thumbnail = dataSnapshot.child("thumb_image").value

        settingsDisplayName.text = displayName.toString()
        settingsDisplayStatus.text = userStatus.toString()

      }

      override fun onCancelled(databaseError: DatabaseError) {

      }
    })

    settingBtnChangeImage.setOnClickListener {
      var galleryIntent = Intent()
      galleryIntent.type = "image/*"
      galleryIntent.action = Intent.ACTION_GET_CONTENT
      startActivityForResult(Intent.createChooser(galleryIntent, "SELECT_IMAGE"), GALLERY_ID)
    }

    settingsBtnChangeStatus.setOnClickListener {
      var intent = Intent(this, StatusActivity::class.java)
      intent.putExtra("status", settingsDisplayStatus.text.toString().trim())
      startActivity(intent)
    }

  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
  }
}
