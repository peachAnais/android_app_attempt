package com.example.teachcode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.teachcode.R
import com.example.teachcode.utilClasses.DatabaseManager

//TODO: create a DB helper

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //create object corresponding to the components on the view
        val email_field = findViewById<EditText>(R.id.email_field)
        val password_field = findViewById<EditText>(R.id.password_field_field)
        val tv_forgot_pwd = findViewById<TextView>(R.id.tv_forgot_password)
        val tv_sign_up = findViewById<TextView>(R.id.tv_sign_up)
        val btn_sign_in = findViewById<Button>(R.id.btn_sign_in)

        val databaseManager = DatabaseManager()

        //create an onClickListener for the sign_in_button; if the credentials are correct, switch to DashboardActivity
        btn_sign_in.setOnClickListener{
            Log.d("sign in button: ", "status: clicked")

            val email = email_field.text.toString().replace(".", "")
            val password = databaseManager.hashPasswordForStorage(password_field.text.toString())
            //we are also going to set the singleton Global user if the authentication in successful
            databaseManager.authenticateUser(this, email, password)
        }

        //create onClickListener for forgot_password. redirect user to forgotPasswordActivity. There they will enter an email to which a password reset link will be sent\
        tv_forgot_pwd.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        //create onCLickListener for no_account. redirect user to SignUpActivity
        tv_sign_up.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

}