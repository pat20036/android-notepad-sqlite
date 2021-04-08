package com.pat.notepad

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pat.notepad.TableInfo.TABLE_NAME

class RecyclerViewAdapter(val context: Context, private val database: SQLiteDatabase) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerItem = layoutInflater.inflate(R.layout.note_item, parent, false)
        return MyViewHolder(recyclerItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemHolder = holder.view.findViewById<CardView>(R.id.noteItem)
        val deleteNoteItem = holder.view.findViewById<ImageView>(R.id.deleteNoteImageView)
        val itemContext = holder.view.context
        itemHolder.setOnClickListener()
        {
            sendUpdateNoteData(holder, itemContext)
        }

        deleteNoteItem.setOnClickListener()
        {
            Log.d("qqqq", "????????????????????????????????????")
            deleteNote(holder)
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


    private fun sendUpdateNoteData(holder: MyViewHolder, context: Context) {
        val noteTitleText = holder.view.findViewById<TextView>(R.id.noteTitleTextView).text
        val noteTextText = holder.view.findViewById<TextView>(R.id.noteTextTextView).text
        val noteId = holder.adapterPosition.plus(1).toString()

        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra("id", noteId)
        intent.putExtra("title", noteTitleText)
        intent.putExtra("text", noteTextText)

        context.startActivity(intent)

    }

    private fun deleteNote(holder: MyViewHolder)
    {
        val noteId = holder.adapterPosition.plus(1).toString()
        database.execSQL("delete from $TABLE_NAME where ${BaseColumns._ID}" + "=$noteId")
        notifyItemRemoved(holder.adapterPosition)

    }


}



