package com.gitzblitz.mychatapp.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gitzblitz.mychatapp.R
import com.gitzblitz.mychatapp.adapters.SectionPagerAdapter
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
    if (intent.extras != null) {
      var userName = intent.extras.get("name")
      Toast.makeText(this, "username " + userName, Toast.LENGTH_LONG).show()
    } else {

    }
  }
}
