package com.pat.notepad

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel

class MainViewModel():ViewModel(){

     fun createDatabase(context: Context): SQLiteDatabase
    {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase
        return db
    }


}