package com.example.teachcode.utilClasses

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.res.integerArrayResource
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teachcode.R
import com.example.teachcode.activities.DashboardActivity
import com.example.teachcode.activities.SignUpActivity
import com.example.teachcode.activities.StartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DatabaseManager {

    private val databaseURL = "https://teach-code-app-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var pathToData: String
    lateinit var sharedPreferences: SharedPreferences

    constructor()

    //functions takes as parameters the context in which the credentials are checked, the email inputted by the user, the password provided by the user
    //function checks if the credentials are correct. in case of any errors, it displays the appropriate message for the user in the form of AlertDialog
    //if the credentials were correct, we also create the singleton current_user
    // the user is directed to the Dashboard activity
    //status: WORKS MOTHERFUCKER
    fun authenticateUser(context: Context, email: String, password: String) {
        val database = FirebaseDatabase.getInstance(databaseURL).getReference("Users/$email/password")

        // Attach a listener to retrieve the data at the location
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value at the location as a String
                val value = dataSnapshot.getValue(String::class.java)
                if(value == password) {
                    sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)

                    //if authentication is succesful, also set the singleton global_user
                    GlobalData.Globals.instance.current_user = Users(email, password)
                    val intent = Intent(context, DashboardActivity::class.java)
                    context.startActivity(intent)
                } else {
                    val dialog = AndroidDialogCreator()
                    dialog.createErrorDialog(context, "Error", "The password you have provided does not match the email. Are you sure you have provided the correct password?", "OK")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
                Log.e("Firebase", "Error getting data", databaseError.toException())
            }
        })
    }

    //TODO: encrypt password and only store the hash in te DB
    //status: works thank FUCK
    fun addUser(context: Context, user: Users) {
        val new_password = hashPasswordForStorage(user.getPassword())
        val new_user = Users(user.getName(), new_password, user.getUsername())
        Log.d("new user", new_user.toString())
        val connection = FirebaseDatabase.getInstance(databaseURL)
        val database = connection.reference
        database.child("Users").child(user.getEmail().replace(".", "")).setValue(new_user)
        Log.d("data status", "succesfully uploaded")

        /*
        val dialog = AndroidDialogCreator()
        dialog.createErrorDialog(context, "Error", "There is already account with this email. If you have forgotten your password, tap Forgot password? to reset it", "OK")
        */
    }
    // Function to hash a password using the SHA-256 algorithm
    fun hashPasswordForStorage(password: String): String {
        try {
            // Create a MessageDigest object for the SHA-256 algorithm
            val md = MessageDigest.getInstance("SHA-256")

            // Apply the hash function to the password bytes
            val hash = md.digest(password.toByteArray())

            // Convert the hash bytes to a hex string
            val hexString = StringBuffer()

            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())

                if (hex.length == 1) {
                    hexString.append('0')
                }

                hexString.append(hex)
            }

            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            // Handle exception here
            return ""
        }
    }


    //update information
    //eg: uer decides to create a bio/choose a profile pic. add this info to the user object
    //TODO: learn how to store photos in firebase + request permission to access gallery
    //TODO: add profile photo and bio fields to Users class
    fun updateUser(){

    }

    //delete the user in Firebase that corresponds to the email
    fun deleteUser(context: Context, user: Users) {
        val database = FirebaseDatabase.getInstance(databaseURL).getReference("Users")
        val user_key = user.getEmail()
        val userRef = database.child(user_key)

        userRef.removeValue()
            .addOnSuccessListener {
                Log.d("deleteUser()", "user deleted successfully")
            }
            .addOnFailureListener{ exception ->
                Log.d(TAG, "Error deleting user object", exception)
            }

    }


    //retrieve value at a given address in the DB
    //statues: works
    //notes: use in lambda functions with value lamba argument being assigned to the string
    fun retrieveValue(path_to_data: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val db = FirebaseDatabase.getInstance(databaseURL)
        val ref = db.getReference(path_to_data)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>().toString()
                Log.d("username in DB from retrieveValue()", value)
                liveData.value = value ?: ""
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Couldn't retrieve value from DB. Error:", error.toException())
            }

        })
        return liveData
    }

}

