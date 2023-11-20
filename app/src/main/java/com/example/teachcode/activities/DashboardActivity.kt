package com.example.teachcode.activities

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.example.teachcode.R
import com.example.teachcode.utilClasses.DatabaseManager
import com.example.teachcode.utilClasses.GlobalData
import com.google.firebase.database.FirebaseDatabase

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        Log.d("DashboardActivity", "starting activity...")

        val learn = findViewById<ImageView>(R.id.img_view_learn)
        val practice = findViewById<ImageView>(R.id.img_view_code)
        val settings = findViewById<ImageView>(R.id.img_view_settings)
        val progress = findViewById<ImageView>(R.id.img_view_progress)
        val tv_username = findViewById<TextView>(R.id.tv_username)

        //database connection for this activity
        val db = DatabaseManager()
        val current_user = GlobalData.Globals.instance.current_user
        val user_email = current_user.getEmail()
        Log.d("DB Manager", "retrieving username...")
        val db_username: LiveData<String> = db.retrieveValue("Users/$user_email/username")
        db_username.observe(this, {value ->
            val stringValue: String = value ?: ""
            tv_username.text = stringValue
            Log.d("retrieved value", stringValue)
        })
        //tv_username.text = db_username.toString()


        learn.setOnClickListener{
            val intent = Intent(this, BrowseCourseActivity::class.java)
            startActivity(intent)
        }

        practice.setOnClickListener{
            val intent = Intent(this, BrowseProblems::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener{
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        progress.setOnClickListener{
            val intent = Intent(this, ProgressStatsActivity::class.java)
            startActivity(intent)
        }
    }

    fun setUsername() {

    }
}