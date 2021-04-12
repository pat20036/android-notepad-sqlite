package com.pat.notepad.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pat.notepad.R
import com.pat.notepad.model.Note
import com.pat.notepad.view.MainFragment
import com.pat.notepad.viewmodel.MainViewModel

class RecyclerAdapter(
    private val noteList: MutableList<Note>,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteCard = view.findViewById<CardView>(R.id.noteItem)
        val noteTitle = view.findViewById<TextView>(R.id.noteTitleTextView)
        val noteDescription = view.findViewById<TextView>(R.id.noteTextTextView)
        val deleteNoteItem = view.findViewById<ImageView>(R.id.deleteNoteImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val noteItem = layoutInflater.inflate(R.layout.note_item, parent, false)
        return MyViewHolder(noteItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val noteItem = noteList[position]
        holder.apply {
            noteTitle.text = (noteItem.title)
            noteDescription.text = (noteItem.description)
        }

        holder.noteCard.setOnClickListener()
        {
            mainViewModel.setSelectedNoteData(noteItem.id, noteItem.title, noteItem.description)
            Log.d("www", mainViewModel.selectedNote.value.toString())
            it.findNavController().navigate(R.id.action_mainFragment_to_noteFragment)

        }

        holder.deleteNoteItem.setOnClickListener()
        {
            mainViewModel.deleteNote(noteItem.id)
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount() = noteList.size


    fun updateList(note: List<Note>) {
        noteList.clear()
        noteList.addAll(note)
        notifyDataSetChanged()
    }



}

