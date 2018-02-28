package com.gitzblitz.mychatapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gitzblitz.mychatapp.fragments.ChatsFragment
import com.gitzblitz.mychatapp.fragments.UsersFragment

/**
 * Created by george.ngethe on 28/02/2018.
 */
class SectionPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
  override fun getItem(position: Int): Fragment {

    when(position){
      0 ->{
        return UsersFragment()
      }

      1 ->{
        return ChatsFragment()
      }
    }
    return null!!
  }

  override fun getCount(): Int {
    return 2
  }
}