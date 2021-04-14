package com.pat.notepad.adapters

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
import com.pat.notepad.viewmodel.MainViewModel

class RecyclerAdapter(
    private val noteList: MutableList<Note>,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteCard: CardView = view.findViewById(R.id.noteItem)
        val noteTitle: TextView = view.findViewById(R.id.noteTitleTextView)
        val noteDescription: TextView = view.findViewById(R.id.noteTextTextView)
        val deleteNoteItem: ImageView = view.findViewById(R.id.deleteNoteImageView)
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

            noteCard.setOnClickListener()
            {
                mainViewModel.setSelectedNoteData(noteItem.id, noteItem.title, noteItem.description)
                it.findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
            }

            deleteNoteItem.setOnClickListener()
            {
                mainViewModel.apply {
                    deleteNote(noteItem.id)
                    getNoteList()
                }

            }
        }

    }

    override fun getItemCount() = noteList.size

    fun updateList(note: List<Note>) {
        noteList.apply {
            clear()
            addAll(note)
        }
        notifyDataSetChanged()
    }


}

