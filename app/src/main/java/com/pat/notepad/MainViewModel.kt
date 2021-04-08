package com.pat.notepad

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.widget.Toast
import androidx.lifecycle.ViewModel

class MainViewModel():ViewModel(){

     fun createDatabase(context: Context): SQLiteDatabase
    {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase
        return db
    }


     fun addNote(context: Context, database:SQLiteDatabase, noteTitle: String, noteText: String, contentValues: ContentValues) {

        if (noteTitle.isNotBlank() && noteText.isNotBlank()) {
            database.insertOrThrow(TableInfo.TABLE_NAME, null, contentValues)
            Toast.makeText(context, "Note saved!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }


     fun updateNote(context: Context, intent: Intent, database: SQLiteDatabase, noteTitle: String, noteText: String, contentValues: ContentValues) {

        if (noteTitle.isNotBlank() && noteText.isNotBlank()) {
            database.update(
                TableInfo.TABLE_NAME,
                contentValues,
                BaseColumns._ID + "=?",
                arrayOf(intent.getStringExtra("id"))
            )
            Toast.makeText(context, "Note saved!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteNote()
    {

    }


}