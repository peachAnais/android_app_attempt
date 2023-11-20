package com.example.teachcode.utilClasses

import android.app.AlertDialog
import android.content.Context

class AndroidDialogCreator {

    //create and show the Android dialog
    fun createErrorDialog(context: Context, title: String, message: String, pos_btn_text: String) {
        val builder = AlertDialog.Builder(context)
        // Set the dialog title
        builder.setTitle(title)

        //builder.setMessage(message)
        builder.setMessage(message)
        // Set the positive button and its click listener
        builder.setPositiveButton("OK") { dialog, which ->
            // Do something when the positive button is clicked
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

}