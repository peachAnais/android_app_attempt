package com.example.teachcode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teachcode.R
import com.example.teachcode.utilClasses.GlobalData
import com.google.android.gms.common.internal.GmsLogger

class CourseOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_overview)

        //set the logo of the current course in the top left corner of the chapter activity
        val current_course_name = GlobalData.Globals.instance.current_course.getCourseName()

        //create the list adapter for the chapter titles
        val pythonChapterTitles = arrayListOf<String>(
            "Syntax", "Comments", "User Input", "Variables",
            "Data Types", "Numbers", "Casting", "Strings",
            "String formatting", "Booleans", "Operators",
            "Lists", "Tuples", "Sets", "Dictionaries", "If...Else",
            "While Loops", "For Loops", "Functions", "Lambda",
            "Arrays", "Classes / Objects", "Inheritance", "Iterators",
            "Polymorphism", "RegEx"
        )

        val cppChapterTitles = arrayListOf<String>(
            "Syntax", "Output", "Comments", "Variables", "User Input",
            "Data Types", "Operator", "Strings", "Math", "Booleans",
            "Conditions", "Switch", "While Loop", "For Loop", "Break / Continue",
            "Arrays", "Structures", "References", "Pointers", "Functions", "Function Parameteres",
            "Function Overloading", "Recursion", "OOP - Object Oriented Programming",
            "Classes / Objects", "Class Methods", "Constructors", "Access Specifiers",
            "Encapsulation", "Inheritance", "Polymorphism", "Files", "Exceptions"
        )

        val javaChapterTitles = arrayListOf<String>(
            "Syntax", "Output", "Comments", "Variables",
            "Data Types", "Type Casting", "Operators",
            "Strings", "Math", "Booleans", "If...Else",
            "Switch", "While Loop", "For Loop", "Break / Continue",
            "Arrays", "Methods", "Method Parameters", "Method Overloading",
            "Scope", "Recursion", "OOP - Object Oriented Programming",
            "Classes / Objects", "Class Attributes", "Class Methods", "Class Constructors",
            "Modifiers", "Encapsulation", "Packages / API", "Inheritance", "Polymorphism",
            "Interface", "Abstraction", "HashMaps", "RegEx", "Lambda"
        )

        val course_logo = findViewById<ImageView>(R.id.img_view_course_logo)
        if(current_course_name == "Python") {
            course_logo.setImageResource(R.drawable.ic_python)
            val listView = findViewById<ListView>(R.id.list_view_chapters)
            val adapter = ArrayAdapter(this, R.layout.text_list_item, pythonChapterTitles)
            listView.adapter = adapter
            listView.setOnItemClickListener{ parent, view, postition, id ->
                val itemTitle = parent.getItemAtPosition(postition) as String
                //once we established which chapter the user wants to see, load the corresponding chapter from the db.
                //start this by initiating an intent that will lead to CourseChapterActivity
                GlobalData.Globals.instance.current_course.setCurrentChapterTitle(itemTitle)
                val intent = Intent(this, CourseChapterActivity::class.java)
                startActivity(intent)
            }
        } else if(current_course_name == "Java") {
            course_logo.setImageResource(R.drawable.ic_java)
            val listView = findViewById<ListView>(R.id.list_view_chapters)
            val adapter = ArrayAdapter(this, R.layout.text_list_item, javaChapterTitles)
            listView.adapter = adapter
            listView.setOnItemClickListener{ parent, view, position, id ->
                val itemTitle = parent.getItemAtPosition(position) as String
                GlobalData.Globals.instance.current_course.setCurrentChapterTitle(itemTitle)
                val intent = Intent(this, CourseChapterActivity::class.java)
                startActivity(intent)
            }
        } else {
            course_logo.setImageResource(R.drawable.ic_cpp)
            val listView = findViewById<ListView>(R.id.list_view_chapters)
            val adapter = ArrayAdapter(this, R.layout.text_list_item, cppChapterTitles)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                val itemTitle = parent.getItemAtPosition(position) as String
                GlobalData.Globals.instance.current_course.setCurrentChapterTitle(itemTitle)
                val intent = Intent(this, CourseChapterActivity::class.java)
                startActivity(intent)
            }
        }

    }

}
    //end of OnCreate()
