package com.gitzblitz.mychatapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gitzblitz.mychatapp.R
import com.gitzblitz.mychatapp.adapters.UsersAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_users.*


/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {
  var mUserDatabaseReference: DatabaseReference? = null

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    return inflater!!.inflate(R.layout.fragment_users, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    var linearlayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    mUserDatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

    usersRecyclerView.setHasFixedSize(true)
    usersRecyclerView.layoutManager = linearlayoutManager
    usersRecyclerView.adapter = UsersAdapter(mUserDatabaseReference!!, context)
  }


}
