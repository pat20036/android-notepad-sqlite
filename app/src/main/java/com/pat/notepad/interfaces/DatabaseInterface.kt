package com.pat.notepad

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.pat.notepad.helpers.DatabaseHelper
import com.pat.notepad.model.Note
import com.pat.notepad.model.SelectedNote

interface DatabaseInterface {
    fun createDatabase(): SQLiteDatabase
    fun getNoteList(): List<Note>
    fun addNewNote(title: String, description: String)
    fun editNote(id: String, title: String, description: String)
    fun deleteNote(id: String)
    fun setSelectedNote(id: String, title: String, description: String): SelectedNote


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
            val id =
                dbCursor.getString(dbCursor.getColumnIndexOrThrow(BaseColumns._ID))
            val title =
                dbCursor.getString(dbCursor.getColumnIndexOrThrow(TableInfo.TABLE_COLUMN_TITLE))
            val description =
                dbCursor.getString(dbCursor.getColumnIndexOrThrow(TableInfo.TABLE_COLUMN_NOTE))
            noteList.add(Note(id, title, description))

        }

        return noteList

    }

    override fun addNewNote(title: String, description: String) {
        val values = ContentValues().apply {
            put(TableInfo.TABLE_COLUMN_TITLE, title)
            put(TableInfo.TABLE_COLUMN_NOTE, description)
        }
        database.insertOrThrow(TableInfo.TABLE_NAME, null, values)
    }

    override fun editNote(id: String, title: String, description: String) {
        val values = ContentValues().apply {
            put(TableInfo.TABLE_COLUMN_TITLE, title)
            put(TableInfo.TABLE_COLUMN_NOTE, description)
        }
        database.update(TableInfo.TABLE_NAME, values, BaseColumns._ID + "=?", arrayOf(id))
    }

    override fun deleteNote(id: String) {
        database.delete(TableInfo.TABLE_NAME, BaseColumns._ID + "=?", arrayOf(id))
    }

    override fun setSelectedNote(id: String, title: String, description: String): SelectedNote {
        return SelectedNote(id, title, description)
    }


}