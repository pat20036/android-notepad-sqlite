package com.pat.notepad

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import com.pat.notepad.helpers.DatabaseHelper
import com.pat.notepad.model.Note
import com.pat.notepad.model.SelectedNote

interface DatabaseInterface {
    fun createDatabase(): SQLiteDatabase
    fun getNoteList(): List<Note>
    fun addNewNote(title: String, description: String)
    fun editNote(id:String, title: String, description: String)
    fun setSelectedNote(id:String, title: String, description: String): SelectedNote


}

class DatabaseInterfaceImpl(private val context: Context) : DatabaseInterface {

    private val database = createDatabase()

    override fun createDatabase(): SQLiteDatabase = DatabaseHelper(context).writableDatabase


    override fun getNoteList(): List<Note> {

        val noteList = mutableListOf<Note>()

        val dbCursor = database.query(
            TableInfo.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (dbCursor.moveToNext()) {
            val title =
                dbCursor.getString(dbCursor.getColumnIndexOrThrow(TableInfo.TABLE_COLUMN_TITLE))
            val description =
                dbCursor.getString(dbCursor.getColumnIndexOrThrow(TableInfo.TABLE_COLUMN_NOTE))
            noteList.add(Note(title, description))

        }

        return noteList

    }

    override fun addNewNote(title:String, description:String) {
        val values = ContentValues().apply {
            put(TableInfo.TABLE_COLUMN_TITLE, title)
            put(TableInfo.TABLE_COLUMN_NOTE, description)
        }

        database.insertOrThrow(TableInfo.TABLE_NAME, null, values)
        Log.d("qqq", values.toString())

    }

    override fun editNote(id: String, title: String, description: String) {
        val values = ContentValues().apply {
            put(TableInfo.TABLE_COLUMN_TITLE, title)
            put(TableInfo.TABLE_COLUMN_NOTE, description)
        }

        database.update(TableInfo.TABLE_NAME, values, BaseColumns._ID+"=?", arrayOf(id))
        Log.d("qqq", values.toString())
        Log.d("bbb", id)

    }

    override fun setSelectedNote(id: String, title: String, description: String): SelectedNote {
        return SelectedNote(id, title, description)
    }


}