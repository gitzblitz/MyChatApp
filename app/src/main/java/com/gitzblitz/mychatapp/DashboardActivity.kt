package com.gitzblitz.mychatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DashboardActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dashboard)

   if (intent.extras != null){
     var userName = intent.extras.get("name")
     title = userName.toString()
   }
  }
}
