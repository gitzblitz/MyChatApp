package com.gitzblitz.mychatapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gitzblitz.mychatapp.R

class DashboardActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dashboard)

    supportActionBar!!.title = "Dashboard"

    if (intent.extras != null) {
      var userName = intent.extras.get("name")
      Toast.makeText(this, "username " + userName, Toast.LENGTH_LONG).show()
    } else {

    }
  }
}
