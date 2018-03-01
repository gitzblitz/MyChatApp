package com.gitzblitz.mychatapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.gitzblitz.mychatapp.R
import com.gitzblitz.mychatapp.adapters.SectionPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

  var sectionPagerAdapter: SectionPagerAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dashboard)


    supportActionBar!!.title = "Dashboard"

    sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager)

    dashBoarViewPager.adapter = sectionPagerAdapter
    dashboardMainTabs.setupWithViewPager(dashBoarViewPager)
    dashboardMainTabs.setTabTextColors(Color.WHITE,Color.GREEN)


//    if (intent.extras != null) {
//      var userName = intent.extras.get("name")
//      Toast.makeText(this, "username " + userName, Toast.LENGTH_LONG).show()
//    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
     super.onCreateOptionsMenu(menu)

    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
     super.onOptionsItemSelected(item)

    if (item != null){
      if (item.itemId == R.id.menuLogout){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this,MainActivity::class.java))
      }

      if (item.itemId == R.id.menuSettings){
        //take users to settings activity
        startActivity(Intent(this,SettingsActivity::class.java))
      }
    }
    return true
  }
}
