package com.gitzblitz.mychatapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.gitzblitz.mychatapp.R
import com.gitzblitz.mychatapp.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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

      override fun populateViewHolder(viewHolder: MessageViewHolder?, model: ChatMessage?, position: Int) {

      }
    }
  }

  class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(chatMessage: ChatMessage) {

    }

  }
}
