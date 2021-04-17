package com.pat.notepad.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pat.notepad.databinding.FragmentNoteBinding
import com.pat.notepad.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var noteId = ""
    private var toastMessage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNoteData()
        observeToastMessage()
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
            val title = binding.noteTitleEditText.text.toString()
            val description = binding.noteTextEditText.text.toString()
            mainViewModel.isDataCorrect(title, description)
            message()
        }
        binding.backArrow.setOnClickListener()
        {
            findNavController().popBackStack()
        }
    }

    private fun updateNoteData() {
        val noteData = mainViewModel.selectedNote.value
        if (noteData != null) {
            noteId = noteData.id
            binding.noteTitleEditText.setText(noteData.title)
            binding.noteTextEditText.setText(noteData.description)
        }
    }

    private fun clearViewModelData() {
        mainViewModel.selectedNote.value = null
    }
    private fun message()
    {
        Toast.makeText(activity?.applicationContext, toastMessage, Toast.LENGTH_LONG).show()
    }

    private fun observeToastMessage()
    {
        mainViewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            toastMessage = it
        })
    }

}