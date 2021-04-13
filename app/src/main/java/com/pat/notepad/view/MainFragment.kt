package com.pat.notepad.view

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pat.notepad.R
import com.pat.notepad.adapters.RecyclerAdapter
import com.pat.notepad.databinding.FragmentMainBinding
import com.pat.notepad.model.Note
import com.pat.notepad.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private lateinit var database: SQLiteDatabase
    private val noteList = mutableListOf<Note>()
    private val adapter by lazy {
        RecyclerAdapter(
            noteList,
            mainViewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = mainViewModel.createDatabase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.adapter = adapter
        updateUI()
    }

    override fun onStart() {
        super.onStart()

        binding.addNoteItem.setOnClickListener()
        {
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getNoteList()
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    private fun updateUI() {
        mainViewModel.noteList.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }


}