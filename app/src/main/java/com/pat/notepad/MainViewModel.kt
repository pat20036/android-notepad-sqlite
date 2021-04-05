package com.pat.notepad

import android.content.Context
import androidx.lifecycle.ViewModel

class MainViewModel():ViewModel(){




     fun createDatabase(context: Context)
    {
        val dbHelper = DatabaseHelper(context)
        dbHelper.writableDatabase
    }


}