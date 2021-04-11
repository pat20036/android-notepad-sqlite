package com.pat.notepad.viewmodel

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pat.notepad.DatabaseInterface
import com.pat.notepad.model.Note
import com.pat.notepad.model.SelectedNote

class MainViewModel(private val databaseInterface: DatabaseInterface) : ViewModel() {

    private val _noteList = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>> get() = _noteList

    private val _selectedNote = MutableLiveData<SelectedNote>()
    val selectedNote: LiveData<SelectedNote> get() = _selectedNote


    fun createDatabase(): SQLiteDatabase {
        return databaseInterface.createDatabase()
    }

    fun getNoteList() {
        _noteList.value = databaseInterface.getNoteList()
        Log.d("qqq", _noteList.value.toString())
    }

    fun addNewNote(title: String, description: String) {
        databaseInterface.addNewNote(title, description)
    }

    fun editNote(id: String, title: String, description: String) {
        databaseInterface.editNote(id, title, description)
    }

    fun setSelectedNoteData(id: String, title: String, description: String)
    {
        val note = databaseInterface.setSelectedNote(id, title, description)
        _selectedNote.value = note

    }


}