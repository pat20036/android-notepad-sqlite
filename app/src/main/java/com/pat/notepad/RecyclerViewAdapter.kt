package com.pat.notepad

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val context: Context, val database: SQLiteDatabase) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerItem = layoutInflater.inflate(R.layout.note_item, parent, false)
        return MyViewHolder(recyclerItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemHolder = holder.view.findViewById<ConstraintLayout>(R.id.noteItem)
        val itemContext = holder.view.context
        itemHolder.setOnClickListener()
        {
            editNote(holder, itemContext)
        }

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


    private fun editNote(holder: MyViewHolder, context: Context)
    {
        val noteId = holder.adapterPosition.plus(1)
        val noteTitleText = holder.view.findViewById<TextView>(R.id.noteTitleTextView).text
        val noteTextText = holder.view.findViewById<TextView>(R.id.noteTextTextView).text

        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra("id", noteId)
        intent.putExtra("title", noteTitleText)
        intent.putExtra("text", noteTextText)

        context.startActivity(intent)

    }


}


class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
