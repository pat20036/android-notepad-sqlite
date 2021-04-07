package com.pat.notepad

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.pat.notepad.TableInfo.TABLE_NAME
import com.pat.notepad.databinding.ActivityNoteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.noteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.saveNote -> {
                saveNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun saveNote() {
        val database = mainViewModel.createDatabase(applicationContext)

        val noteTitle = binding.noteTitleEditText.text.toString()
        val noteText = binding.noteTextEditText.text.toString()
        if (noteTitle.isNotBlank() && noteText.isNotBlank()) {
            val contentValues = ContentValues().apply {
                put("Title", noteTitle)
                put("Note", noteText)
            }

            database.insertOrThrow(TABLE_NAME, null, contentValues)
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()

        }
        else
        {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }


    }

}