package com.gitzblitz.mychatapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.gitzblitz.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
  var mAuth: FirebaseAuth? = null
  var mDatabase: DatabaseReference? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    mAuth = FirebaseAuth.getInstance()


    loginButtonId.setOnClickListener {
      var email = loginEmailEdt.text.toString().trim()
      var password = loginPasswordEdt.text.toString().trim()

      if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
        loginUser(email, password)
      }else{
        Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_LONG).show()
      }

    }
  }

  private fun loginUser(email: String, password: String): Unit {

    mAuth!!.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener {
      task: Task<AuthResult> ->
          if (task.isSuccessful){
            var userName = email.split("@")[0]
            var dashboardIntent = Intent(this, DashboardActivity::class.java)
            dashboardIntent.putExtra("name", userName)
            startActivity(dashboardIntent)
            finish()

          }else{
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
          }
    }

  }
}
