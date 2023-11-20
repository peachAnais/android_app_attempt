package com.example.teachcode.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.teachcode.databinding.ActivitySignUpBinding
import com.example.teachcode.utilClasses.AndroidDialogCreator
import com.example.teachcode.utilClasses.DatabaseManager
import com.example.teachcode.utilClasses.Users
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    //set lateinit vars to be used
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: DatabaseReference
    private lateinit var connection: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //TODO: also store a profile picture

        //send user data to database if the user is valid when Register button is clicked
        //if there are any errors in the user data, show AlertDialog that mentions the specific error that occurred

        binding.registerButton.setOnClickListener{

            val name = binding.regNameField.text.toString()
            val email = binding.regEmailField.text.toString()
            val pwd = binding.regPwdField.text.toString()
            val conf_pwd = binding.regConfPwdField.text.toString()
            val username = binding.regUsernameField.text.toString()

            val current_user = Users(name, email, pwd, username)
            Log.d("current_user:", current_user.toString())

            Log.d("Register button was clicked: ","checking if the data provided is OK")

            if(current_user.validateUser(current_user, conf_pwd) == 0) {

                Log.d("User data: ", "user provided correct data")
                //send data to database and create a new user there
                val databaseManager = DatabaseManager()
                databaseManager.addUser(this, current_user)
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)

            //the user input data was not OK
            } else if(current_user.validateUser(current_user, conf_pwd) == 1) {
                Log.d("Register button was clicked: ", "one of the fields was empty")
                //email was invalid
                val message = "One of the fields is empty. Please make sure to fill out all information"
                val dialogCreator = AndroidDialogCreator()
                dialogCreator.createErrorDialog(this, "Error", message, "OK")
            } else if(current_user.validateUser(current_user, conf_pwd) == 2) {
                Log.d("Register button was clicked: ", "email was determined to be invalid after regex verification")
                //password and confirmation passwords did not match
                val message = "The email you have provided is invalid. Please provide a valid email address."
                val dialogCreator = AndroidDialogCreator()
                dialogCreator.createErrorDialog(this, "Error", message, "OK")
            } else if(current_user.validateUser(current_user, conf_pwd) == 3) {
                Log.d("Resgister button was clicked: ", "passwords did not match")
                val message = "The passwords you have provided do not match. Please make sure the confirmation password matches the password you have first provided."
                val dialogCreator = AndroidDialogCreator()
                dialogCreator.createErrorDialog(this, "Error", message, "OK")
            }
        }
    }
    //end of onCreate()

}