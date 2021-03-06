package com.pat.notepad.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pat.notepad.SqlQueries
import com.pat.notepad.TableInfo


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, TableInfo.TABLE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SqlQueries.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SqlQueries.SQL_DELETE_TABLE)
        onCreate(db)
    }

}