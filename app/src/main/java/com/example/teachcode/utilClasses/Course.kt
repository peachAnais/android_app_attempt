package com.example.teachcode.utilClasses

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlin.properties.Delegates

//the Course object we create in BrowseCourseActivity must be visible from CourseOverviewActivity and CourseChapterActivity as well
//you can find the global user as the current_course object in GlobalData class
class Course {

    private lateinit var course_name: String
    private lateinit var logo: ByteArray
    private lateinit var course_reference: DatabaseReference
    private lateinit var current_chapter: String

    constructor(course_name: String){
        this.course_name = course_name
    }

    //set the chapter title
    fun setCurrentChapterTitle(title: String){
        this.current_chapter = title
    }

    //set the course_reference
    fun setCourseReference(ref_course_name: String){
        this.course_reference = FirebaseDatabase.getInstance("https://teach-code-app-default-rtdb.europe-west1.firebasedatabase.app/Courses").getReference(ref_course_name)
    }

    //getters and setters
    fun getCourseName(): String {
        return this.course_name
    }

    //set the image of the current course
    fun setCourseLogo(view: ImageView, course_name: String) {

        val storageRef = FirebaseStorage.getInstance("gs://teach-code-app.appspot.com").reference
        val imageReference = storageRef.child("images/ic_$course_name.png")

        val ONE_MEGABYTE: Long = 1024 * 1024

        imageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {bytes ->
            //download the image
            val image_data = bytes
            this.logo = image_data
        }.addOnFailureListener {exception ->
            Log.e(TAG, "Error downloading image", exception)
        }

    }

    //set up course chapter from CourseOverviewActivity
    fun setCourseChapter(chapter_name: String){

    }

    //go to next chapter after pressing "Next" button in a CourseChapterActivity
    fun nextChapter(current_chapter: String) {
        val database = FirebaseDatabase.getInstance("https://teach-code-app-default-rtdb.europe-west1.firebasedatabase.app/Courses").getReference(current_chapter)

        val childID = current_chapter

        database.orderByKey().startAt(childID).limitToFirst(2).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children.toList()
                if(children.size == 2) {
                    val nextChild = children[1]
                    val nextChildKey = nextChild.key
                    val nextChildValue = nextChild.value
                }
                //you ave reached the end of the course
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Couldn't load the next chapter. Error:", error.toException())
            }

        })

    }

    //send the users answer to the exercise they are solving and see if it's the same as the answer stored in the DB
    fun checkUserAnswer(user_answer: String, course: Course, exercise_id: String): Boolean{
        val databaseManager = DatabaseManager()
        val name = course.getCourseName()
        val correct_val = databaseManager.retrieveValue("Courses/$name/exercise_id").toString()
        return correct_val == user_answer
    }

}