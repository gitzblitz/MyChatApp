package com.gitzblitz.mychatapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.gitzblitz.mychatapp.R
import com.gitzblitz.mychatapp.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
  var userId: String? = null
  var mDatabase: DatabaseReference? = null
  var mFirebaseUser: FirebaseUser? = null
  var linearLayoutManager: LinearLayoutManager? = null
  var mFirebaseAdapter: FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>? = null

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat)
    mFirebaseUser = FirebaseAuth.getInstance().currentUser

    userId = intent.extras.getString("userId")
    linearLayoutManager = LinearLayoutManager(this)
    linearLayoutManager!!.stackFromEnd = true

    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    mDatabase = FirebaseDatabase.getInstance().reference

    mFirebaseAdapter = object : FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(ChatMessage::class.java, R.layout.item_message,
        MessageViewHolder::class.java,
        mDatabase!!.child("messages")) {

      override fun populateViewHolder(viewHolder: MessageViewHolder?, chatMessage: ChatMessage, position: Int) {
          if (chatMessage.text != null){
              viewHolder!!.bindView(chatMessage)
            var currentUserId =  mFirebaseUser!!.uid

            var isMe: Boolean = chatMessage.id!!.equals(currentUserId)

            if (isMe){
              // to left
              viewHolder.profileImageViewRight!!.visibility = View.VISIBLE
              viewHolder.profileImage!!.visibility = View.GONE
              viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.END)
              viewHolder.messengerTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.END)

              // get image url for me
              mDatabase!!.child("Users").child(currentUserId).addValueEventListener(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                 var imageUrl = dataSnapshot.child("thumb_image").value.toString()
                  var displayName = dataSnapshot.child("display_name").value

                  viewHolder.messengerTextView!!.text = displayName.toString()
                  Picasso.with(viewHolder.profileImage!!.context)
                      .load(imageUrl)
                      .placeholder(R.drawable.profile_img)
                      .into(viewHolder.profileImageViewRight)
                }

                override fun onCancelled(error: DatabaseError) {
                  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
              })

            }
            else{
              // to the other side

              viewHolder.profileImageViewRight!!.visibility = View.GONE
              viewHolder.profileImage!!.visibility = View.VISIBLE
              viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.START)
              viewHolder.messengerTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.START)

              // get image url for me
              mDatabase!!.child("Users").child(userId).addValueEventListener(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                  var imageUrl = dataSnapshot.child("thumb_image").value.toString()
                  var displayName = dataSnapshot.child("display_name").value

                  viewHolder.messengerTextView!!.text = displayName.toString()
                  Picasso.with(viewHolder.profileImage!!.context)
                      .load(imageUrl)
                      .placeholder(R.drawable.profile_img)
                      .into(viewHolder.profileImage)
                }

                override fun onCancelled(error: DatabaseError) {
                  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
              })


            }
          }
      }
    }
    // set the recyclerview
    chatRecyclerView.layoutManager = linearLayoutManager

    chatRecyclerView.adapter = mFirebaseAdapter

    sendMessageButton.setOnClickListener {
      if (!intent.extras.get("name").toString().equals("")){
          var currentUserName = intent.extras.get("name")
        var mCurrentUserID = mFirebaseUser!!.uid

        var message = ChatMessage(mCurrentUserID,
            addMessageEdit.text.toString().trim(),
            currentUserName.toString().trim())

        mDatabase!!.child("messages").push().setValue(message)

        addMessageEdit.setText("")

      }
    }



  }

  class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var messageTextView: TextView? = null
    var messengerTextView: TextView? = null
    var profileImage: CircleImageView? = null
    var profileImageViewRight: CircleImageView? = null

    fun bindView(chatMessage: ChatMessage) {
      messageTextView = itemView.findViewById(R.id.messageTextView)
      messengerTextView = itemView.findViewById(R.id.messengerTextView)
      profileImage = itemView.findViewById(R.id.messageImageView)
      profileImageViewRight = itemView.findViewById(R.id.messageImageViewRight)

      messengerTextView!!.text = chatMessage.name
      messageTextView!!.text = chatMessage.text

    }

  }
}
