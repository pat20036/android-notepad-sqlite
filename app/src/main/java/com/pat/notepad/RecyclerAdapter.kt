package com.pat.notepad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val noteList: MutableList<Note>, private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteCard = view.findViewById<CardView>(R.id.noteItem)
        val noteTitle = view.findViewById<TextView>(R.id.noteTitleTextView)
        val noteDescription = view.findViewById<TextView>(R.id.noteTextTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val noteItem = layoutInflater.inflate(R.layout.note_item, parent, false)
        return MyViewHolder(noteItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val noteItem = noteList[position]
        holder.apply {
            noteTitle.setText(noteItem.title)
            noteDescription.setText(noteItem.description)
        }

        holder.noteCard.setOnClickListener()
        {
            val noteId = position+1
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("id", noteId.toString())
            intent.putExtra("title", holder.noteTitle.text)
            intent.putExtra("description", holder.noteDescription.text)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = noteList.size


    fun updateList(note: List<Note>) {
        noteList.clear()
        noteList.addAll(note)
        notifyDataSetChanged()
    }




}