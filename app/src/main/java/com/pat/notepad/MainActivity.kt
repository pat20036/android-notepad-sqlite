package com.pat.notepad

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pat.notepad.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var database: SQLiteDatabase
    private val noteList = mutableListOf<Note>()
    private val adapter by lazy { RecyclerAdapter(noteList, applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter

        database = mainViewModel.createDatabase()

        updateNoteList()

    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getNoteList()
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.saveNote -> {
                addNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addNote() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }

    private fun editNote()
    {

    }

    private fun updateNoteList() {
        mainViewModel.noteList.observe(this, Observer {
            adapter.updateList(it)
        })

    }


}