package com.pat.notepad

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.pat.notepad.TableInfo.TABLE_COLUMN_TITLE
import com.pat.notepad.TableInfo.TABLE_COLUMN_NOTE
import com.pat.notepad.TableInfo.TABLE_NAME
import com.pat.notepad.databinding.ActivityNoteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var  database:SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.noteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = mainViewModel.createDatabase(applicationContext)

        if (intent.hasExtra("title")) {
            binding.noteTitleEditText.setText(intent.getStringExtra("title"))
        }
        if (intent.hasExtra("text")) {
            binding.noteTextEditText.setText(intent.getStringExtra("text"))
        }


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
        val noteTitle = binding.noteTitleEditText.text.toString()
        val noteText = binding.noteTextEditText.text.toString()
        val contentValues = ContentValues().apply {
            put(TABLE_COLUMN_TITLE, noteTitle)
            put(TABLE_COLUMN_NOTE, noteText)
        }
        if (intent.hasExtra("id")) {
            updateNote(noteTitle, noteText, contentValues)
        } else {
            addNote(noteTitle, noteText,contentValues)
        }
    }

    private fun addNote(noteTitle: String, noteText: String, contentValues: ContentValues) {

        if (noteTitle.isNotBlank() && noteText.isNotBlank()) {
            database.insertOrThrow(TABLE_NAME, null, contentValues)
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNote(noteTitle: String, noteText: String, contentValues: ContentValues) {

        if (noteTitle.isNotBlank() && noteText.isNotBlank()) {
            database.update(
                TABLE_NAME,
                contentValues,
                BaseColumns._ID + "=?",
                arrayOf(intent.getStringExtra("id"))
            )
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }
}