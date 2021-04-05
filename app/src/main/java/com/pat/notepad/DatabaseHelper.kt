package com.pat.notepad

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.pat.notepad.BasicCommands.SQL_CREATE_TABLE
import com.pat.notepad.BasicCommands.SQL_DELETE_TABLE
import com.pat.notepad.TableInfo.TABLE_COLUMN_NAME
import com.pat.notepad.TableInfo.TABLE_COLUMN_NOTE
import com.pat.notepad.TableInfo.TABLE_NAME

object TableInfo : BaseColumns {
    const val TABLE_NAME = "notes"
    const val TABLE_COLUMN_NAME = "title"
    const val TABLE_COLUMN_NOTE = "note"

}

object BasicCommands {
    const val SQL_CREATE_TABLE =
        "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TABLE_COLUMN_NAME} TEXT NOT NULL," +
                "${TABLE_COLUMN_NOTE} TEXT NOT NULL)"


    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, TABLE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

}