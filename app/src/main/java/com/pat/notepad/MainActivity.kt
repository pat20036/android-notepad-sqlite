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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pat.notepad.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)


        database = mainViewModel.createDatabase(applicationContext)

    }

    override fun onResume() {
        super.onResume()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = RecyclerViewAdapter(applicationContext, database)
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


}