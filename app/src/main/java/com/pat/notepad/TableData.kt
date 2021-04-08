package com.pat.notepad

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

import com.pat.notepad.TableInfo.TABLE_NAME

object TableInfo : BaseColumns {
    const val TABLE_NAME = "Notes"
    const val TABLE_COLUMN_TITLE = "Title"
    const val TABLE_COLUMN_NOTE = "Note"

}

object SqlQueries
{
    const val SQL_CREATE_TABLE =
        "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_TITLE} TEXT," +
                "${TableInfo.TABLE_COLUMN_NOTE} TEXT)"



    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}





