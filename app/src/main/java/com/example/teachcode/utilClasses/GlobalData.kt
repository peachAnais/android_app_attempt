package com.example.teachcode.utilClasses

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GlobalData {

    var current_course: Course = Course("Not initialized yet")
    var current_user: Users = Users("not initialized", "not initialized")


    object Globals {
        val instance = GlobalData()
    }

}