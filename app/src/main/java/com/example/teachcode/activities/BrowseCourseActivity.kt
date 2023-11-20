package com.example.teachcode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.example.teachcode.R
import com.example.teachcode.utilClasses.Course
import com.example.teachcode.utilClasses.GlobalData
import javax.microedition.khronos.opengles.GL

class BrowseCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_course)


        //in this activity, we redirect the user to the screen that shows a more detailed overview of the course
        //this should happen regardless of whether the description of the course is saved or the log
        //when the user clicks on the corresponding ScrollView / ImageView also set the singleton for the current course the user is looking at

        val python = findViewById<ImageView>(R.id.img_view_python)
        val java = findViewById<ImageView>(R.id.img_view_java)
        val cpp = findViewById<ImageView>(R.id.img_view_cpp)
        python.setOnClickListener{
            GlobalData.Globals.instance.current_course = Course("Python")
            val intent = Intent(this, CourseOverviewActivity::class.java)
            startActivity(intent)
        }
        java.setOnClickListener{
            GlobalData.Globals.instance.current_course = Course("Java")
            val intent = Intent(this, CourseOverviewActivity::class.java)
            startActivity(intent)
        }
        cpp.setOnClickListener{
            GlobalData.Globals.instance.current_course = Course("C++")
            val intent = Intent(this, CourseOverviewActivity::class.java)
            startActivity(intent)
        }



        val python_desc = findViewById<ScrollView>(R.id.scroll_python_description)
        val java_desc = findViewById<ScrollView>(R.id.scroll_java_description)
        val cpp_view = findViewById<ScrollView>(R.id.scroll_cpp_description)
        python_desc.setOnClickListener {
            GlobalData.Globals.instance.current_course = Course("Python")
            val intent = Intent(this, CourseOverviewActivity::class.java)
            startActivity(intent)
        }
        java_desc.setOnClickListener {
            GlobalData.Globals.instance.current_course = Course("Java")
            val intent = Intent(this, CourseOverviewActivity::class.java)
            startActivity(intent)
        }
        cpp_view.setOnClickListener {
            GlobalData.Globals.instance.current_course = Course("C++")
            val intent = Intent(this, CourseOverviewActivity::class.java)
            startActivity(intent)
        }
    }
}