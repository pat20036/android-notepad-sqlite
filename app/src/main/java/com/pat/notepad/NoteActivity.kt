package com.pat.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.pat.notepad.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.noteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
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

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}