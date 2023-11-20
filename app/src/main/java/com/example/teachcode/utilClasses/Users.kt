package com.example.teachcode.utilClasses

import android.app.Notification.BigPictureStyle
import android.util.Log
import java.sql.Blob

//the current user should be visible throughout all activities after login
//you can find the global user in the GlobalData class
class Users {
    private lateinit var name: String
    private var email: String = ""
    private var password: String
    private lateinit var username: String
    private lateinit var bio: String
    private lateinit var profile_pic: ByteArray

    constructor(name: String, email: String, password: String, username: String) {
        this.name = name
        this.email = email
        this.password = password
        this.username = username
    }

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

    constructor(name: String, password: String, username: String) {
        this.name = name
        this.password = password
        this.username = username
    }

    //return vals: 0 => everything A-okay; 1 => there is one empty filed; 2 => invalid email; 3 => provided passwords do not match
    fun validateUser(user: Users, conf_pwd: String): Int {
        if (user.email.isEmpty() || user.password.isEmpty() ||
            conf_pwd.isEmpty() || user.name.isEmpty() ||
            user.username.isEmpty()) {
            Log.d("Users class: ", "one of the fields was empty")
            // At least one field is empty
            return 1
        }

        val emailRegex = Regex("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")
        if (!emailRegex.matches(user.email)) {
            // Email doesn't match regex pattern
            Log.d("Users class:", "email address in invalid after regex verification")
            return 2
        }

        if (user.password != conf_pwd) {
            // Passwords don't match
            Log.d("Users class:", "password and conf_pwd do not match")
            return 3
        }

        // All checks passed
        Log.d("Users class: ", "everything is A-OK")
        return 0
    }

    override fun toString(): String {
        return "Users(name='$name', email='$email', password='$password', username='$username')"
    }

    //getters and setters
    fun getEmail(): String {
        return email
    }
    fun setEmail(str: String) {
        this.email = str
    }
    fun getName(): String {
        return name
    }
    fun getPassword(): String {
        return password
    }
    fun setPassword(password: String) {
        this.password = password
    }
    fun getUsername(): String {
        return username
    }

}