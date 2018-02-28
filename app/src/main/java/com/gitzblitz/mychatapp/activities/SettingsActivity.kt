package com.gitzblitz.mychatapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gitzblitz.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File

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

    mStorageReference = FirebaseStorage.getInstance().reference

    mDatabase!!.addValueEventListener(object : ValueEventListener {

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        var displayName = dataSnapshot.child("display_name").value
        var image = dataSnapshot.child("image").value.toString()
        var userStatus = dataSnapshot.child("status").value
        var thumbnail = dataSnapshot.child("thumb_image").value

        settingsDisplayName.text = displayName.toString()
        settingsDisplayStatus.text = userStatus.toString()

        if (!image!!.equals("default")){
          Picasso.with(applicationContext)
              .load(image)
              .placeholder(R.drawable.profile_img)
              .into(settingsProfileImageView)
        }
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

    if (requestCode == GALLERY_ID && resultCode == Activity.RESULT_OK) {
      var image: Uri = data!!.data

      CropImage.activity(image)
          .setAspectRatio(1, 1)
          .start(this)

    }

    if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      val result = CropImage.getActivityResult(data)
      if (resultCode === Activity.RESULT_OK) {
        val resultUri = result.uri

        var userId = mCurrentUser!!.uid
        var thumbFile = File(resultUri.path)

        var thumbBitmap = Compressor(this)
            .setMaxWidth(200)
            .setMaxHeight(200)
            .setQuality(65)
            .compressToBitmap(thumbFile)

        // now upload to firebase
        //create byte array output stream
        var byteArray = ByteArrayOutputStream()
        thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)

        var thumbByteArray: ByteArray
        thumbByteArray = byteArray.toByteArray()

        var filePath = mStorageReference!!.child("chat_profile_images")
            .child(userId + ".jpg")

        // create another directory for thumb images

        var thumbFilePath = mStorageReference!!.child("chat_profile_images")
            .child("thumbs")
            .child(userId + ".jpg")

        filePath.putFile(resultUri)
            .addOnCompleteListener { task: Task<UploadTask.TaskSnapshot> ->

              if (task.isSuccessful) {
                // get the picture url
                var downloadUrl = task.result.downloadUrl.toString()

                // uploads task
                var uploadTask: UploadTask = thumbFilePath
                    .putBytes(thumbByteArray)
                uploadTask.addOnCompleteListener {

                  task: Task<UploadTask.TaskSnapshot> ->

                  var thumbUrl = task.result.downloadUrl.toString()
                  if (task.isSuccessful) {
                    var updateObject = HashMap<String, Any>()
                    updateObject.put("image", downloadUrl)
                    updateObject.put("thumb_image", thumbUrl)

                    // save profile image

                    mDatabase!!.updateChildren(updateObject).addOnCompleteListener { task: Task<Void> ->
                      if (task.isSuccessful) {
                        Toast.makeText(this, "Profile image saved", Toast.LENGTH_LONG).show()
                      } else {
                        Toast.makeText(this, " Profile image not saved. Please try again", Toast.LENGTH_LONG).show()
                      }
                    }

                  } else {
                    Toast.makeText(this, " Thumbnail uploaded", Toast.LENGTH_LONG).show()
                  }
                }
              } else {
                Toast.makeText(this, " Image not uploaded", Toast.LENGTH_LONG).show()
              }
            }
      }
    }
    super.onActivityResult(requestCode, resultCode, data)

  }
}
