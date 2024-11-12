package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.ui.adapters.NoteAdapter

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setUpListeners()
        getData()
    }

    private fun init() {
        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun setUpListeners() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }
    }

    private fun getData() {
        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { listWithData ->
            noteAdapter.submitList(listWithData)
        }
    }
}