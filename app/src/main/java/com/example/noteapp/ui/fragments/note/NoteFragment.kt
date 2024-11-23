package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.ui.adapters.NoteAdapter
import com.example.noteapp.ui.interfaces.OnClickItem
import com.example.noteapp.utils.PreferenceHelper

class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private val sharedPreferences = PreferenceHelper()
    private val noteAdapter = NoteAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences.unit(requireContext())
        init()
        setUpListeners()
        getData()
        initMenu()
    }

    private fun initMenu() {
        binding.navView.setupWithNavController(findNavController())
    }

    private fun init() {
        val apply = binding.rvNote.apply {
            if (sharedPreferences.isGridLayout) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                binding.toLine.visibility = View.VISIBLE
            } else {
                layoutManager = LinearLayoutManager(requireContext())
                binding.toGrid.visibility = View.VISIBLE
            }
            adapter = noteAdapter
        }
    }

    private fun setUpListeners() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }
        toGrid.setOnClickListener {
            toLine.visibility = View.VISIBLE
            toGrid.visibility = View.GONE
            rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
            sharedPreferences.isGridLayout = true
        }
        toLine.setOnClickListener {
            toLine.visibility = View.GONE
            toGrid.visibility = View.VISIBLE
            rvNote.layoutManager = LinearLayoutManager(requireContext())
            sharedPreferences.isGridLayout = false
        }
        drawerBtn.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }
    }

    private fun getData() {
        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { listWithData ->
            noteAdapter.submitList(listWithData)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("delete ${noteModel.title}\n${noteModel.desc}  ${noteModel.time}")
            setPositiveButton("delete") { dialog, _ ->
                App.appDataBase!!.noteDao().deleteNote(noteModel)
            }
            setNegativeButton("cancel") { dialog, _ ->
                dialog.cancel()
            }
            setNeutralButton("ignore") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToDetailNoteFragment(noteModel.id)
        findNavController().navigate(action)
    }
}