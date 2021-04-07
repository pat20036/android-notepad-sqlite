package com.pat.notepad

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val database: SQLiteDatabase) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerItem = layoutInflater.inflate(R.layout.note_item, parent, false)
        return MyViewHolder(recyclerItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getDatabaseData(holder)
    }

    override fun getItemCount(): Int {
        return getDatabaseSize()
    }


    private fun getDatabaseSize(): Int {
        val dbCursor = database.query(
            TableInfo.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val dbSize = dbCursor.count
        dbCursor.close()

        return dbSize
    }

    private fun getDatabaseData(holder: MyViewHolder) {
        val noteTitle = holder.view.findViewById<TextView>(R.id.noteTitleTextView)
        val noteText = holder.view.findViewById<TextView>(R.id.noteTextTextView)

        val dbCursor = database.query(
            TableInfo.TABLE_NAME,
            null,
            BaseColumns._ID + "=?",
            arrayOf(holder.adapterPosition.plus(1).toString()),
            null,
            null,
            null
        )

        if (dbCursor.moveToFirst()) {
            if (!(dbCursor.getString(1).isNullOrEmpty() || dbCursor.getString(2).isNullOrEmpty())) {
                noteTitle.setText(dbCursor.getString(1))
                noteText.setText(dbCursor.getString(2))
            }
        }
    }


}


class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
