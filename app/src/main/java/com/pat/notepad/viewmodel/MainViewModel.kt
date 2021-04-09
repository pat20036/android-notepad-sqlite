package com.pat.notepad.viewmodel

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pat.notepad.DatabaseInterface
import com.pat.notepad.model.Note

class MainViewModel(private val databaseInterface: DatabaseInterface) : ViewModel() {

    private val _noteList = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>> get() = _noteList

    fun createDatabase(): SQLiteDatabase {
        return databaseInterface.createDatabase()
    }

    fun getNoteList()
    {
        _noteList.value =  databaseInterface.getNoteList()
        Log.d("qqq", _noteList.value.toString())
    }





}