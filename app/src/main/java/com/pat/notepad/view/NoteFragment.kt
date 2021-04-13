package com.pat.notepad.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pat.notepad.databinding.FragmentNoteBinding
import com.pat.notepad.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var noteId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNoteData()
        if (noteId != "") {
            binding.noteToolbar.title = "Edit note"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearViewModelData()
    }


    override fun onStart() {
        super.onStart()
        binding.saveNoteItem.setOnClickListener()
        {
            isDataCorrect()
        }
        binding.backArrow.setOnClickListener()
        {
            findNavController().popBackStack()
        }

    }

    private fun updateNoteData() {
        val noteData = mainViewModel.selectedNote.value
        Log.d("TAG", noteData.toString())
        if (noteData != null) {
            noteId = noteData.id
            binding.noteTitleEditText.setText(noteData.title)
            binding.noteTextEditText.setText(noteData.description)
        }
    }

    private fun clearViewModelData() {
        mainViewModel.selectedNote.value = null
    }

    private fun isDataCorrect()
    {
        val title = binding.noteTitleEditText.text.toString()
        val description = binding.noteTextEditText.text.toString()
        if (title.isNotBlank() || description.isNotBlank()) {
            if (noteId == "") {
                mainViewModel.addNewNote(title, description)
                Toast.makeText(activity?.applicationContext, "Added!", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            } else {
                mainViewModel.editNote(noteId, title, description)
                Toast.makeText(activity?.applicationContext, "Edited!", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }

        } else {
            Toast.makeText(
                activity?.applicationContext,
                "Fields cannot be empty!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}