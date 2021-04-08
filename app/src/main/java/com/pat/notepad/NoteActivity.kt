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
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.noteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add note"

        database = mainViewModel.createDatabase(applicationContext)

        if (intent.hasExtra("title")) {
            binding.noteTitleEditText.setText(intent.getStringExtra("title"))
            supportActionBar?.title = "Edit note"
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
            mainViewModel.updateNote(
                applicationContext,
                intent,
                database,
                noteTitle,
                noteText,
                contentValues
            )

            finish()
        } else {
            mainViewModel.addNote(applicationContext, database, noteTitle, noteText, contentValues)

            finish()
        }

    }


}