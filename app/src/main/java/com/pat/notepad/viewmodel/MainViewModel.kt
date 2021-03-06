package com.pat.notepad.viewmodel

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.pat.notepad.DatabaseInterface
import com.pat.notepad.NavigationCommander
import com.pat.notepad.NavigationCommanderImpl
import com.pat.notepad.model.Note
import com.pat.notepad.model.SelectedNote

class MainViewModel(private val databaseInterface: DatabaseInterface, private val navigationCommander: NavigationCommander): ViewModel() {

    private val _noteList = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>> get() = _noteList

    val selectedNote = MutableLiveData<SelectedNote>()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun createDatabase(): SQLiteDatabase {
        return databaseInterface.createDatabase()
    }

    fun getNoteList() {
        _noteList.value = databaseInterface.getNoteList()
    }

    private fun addNewNote(title: String, description: String) {
        databaseInterface.addNewNote(title, description)
    }

    private fun editNote(id: String, title: String, description: String) {
        databaseInterface.editNote(id, title, description)
    }

    fun deleteNote(id: String) {
        databaseInterface.deleteNote(id)
    }

    fun setSelectedNoteData(id: String, title: String, description: String) {
        val note = databaseInterface.setSelectedNote(id, title, description)
        selectedNote.value = note

    }


    fun isDataCorrect(title: String, description: String) {
        val noteData = selectedNote.value
        if (title.isNotBlank() || description.isNotBlank()) {
            if (noteData == null) {
                addNewNote(title, description)
                navigationCommander.popBackStack()
                _toastMessage.value = "Added!"
            } else {
                editNote(noteData.id, title, description)
                navigationCommander.popBackStack()
                _toastMessage.value = "Edited!"
            }
        }
        else
        {
            _toastMessage.value = "Fields cannot be empty!"
        }

    }

}