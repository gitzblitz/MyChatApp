package com.gitzblitz.mychatapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.gitzblitz.mychatapp.R
import com.gitzblitz.mychatapp.models.Users
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by george.ngethe on 28/02/2018.
 */
class UsersAdapter(databaseQuery: DatabaseReference, var context: Context): FirebaseRecyclerAdapter<Users,UsersAdapter.ViewHolder>(Users::class.java,
    R.layout.users_row,
    UsersAdapter.ViewHolder::class.java,
    databaseQuery) {
  override fun populateViewHolder(viewHolder: UsersAdapter.ViewHolder?, user: Users?, position: Int) {

    var userId = getRef(position).key
    viewHolder!!.bindView(user!!, context)

    viewHolder.itemView.setOnClickListener {
      //TODO create popup dialog where user can choose to see message or view profile
      Toast.makeText(context, "User row clicked $userId", Toast.LENGTH_LONG).show()
    }
  }


   class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    var userNameTxt: String? = null
    var userStatusTxt: String? = null
    var userProfilePicLink: String? = null


    fun bindView(user: Users, context: Context){
      var userName = itemView.findViewById<TextView>(R.id.userName)
      var userStatus = itemView.findViewById<TextView>(R.id.userStatus)
      var userProfilePic = itemView.findViewById<CircleImageView>(R.id.usersProfileImageView)

      // set the strings

      userNameTxt = user.display_name
      userStatusTxt = user.status
      userProfilePicLink = user.thumb_image

      userName.text = user.display_name
      userStatus.text = user.status

      Picasso.with(context)
          .load(userProfilePicLink)
          .placeholder(R.drawable.profile_img)
          .into(userProfilePic)

    }

  }


}