package com.pat.notepad

import android.content.ClipDescription
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.pat.notepad.TableInfo.TABLE_COLUMN_TITLE
import com.pat.notepad.TableInfo.TABLE_COLUMN_NOTE
import com.pat.notepad.databinding.ActivityNoteBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private val databaseInterface: DatabaseInterface by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.noteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add note"


        if (intent.hasExtra("title")) {
            binding.noteTitleEditText.setText(intent.getStringExtra("title"))
            supportActionBar?.title = "Edit note"
        }
        if (intent.hasExtra("description")) {
            binding.noteTextEditText.setText(intent.getStringExtra("description"))
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saveNote -> {
                val title = binding.noteTitleEditText.text.toString()
                val description = binding.noteTextEditText.text.toString()
                if (intent.hasExtra("id")) {
                    editNote(title,description)
                } else {
                    addNote(title, description)
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addNote(title: String, description: String) {
        databaseInterface.addNewNote(title, description)
    }

    private fun editNote(title: String, description: String) {
        val id = intent.getStringExtra("id")
        databaseInterface.editNote(id.toString(), title, description)
    }


}