package com.example.noteapp.ui.fragments.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentDetailNoteBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class DetailNoteFragment : Fragment() {

    private lateinit var binding: FragmentDetailNoteBinding
    private var noteId: Int = -1
    private var color = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        startUpdatingTime()
        updateNote()
    }

    private fun updateNote() {
        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val modelGotWithId = App.appDataBase?.noteDao()?.getById(noteId)
            modelGotWithId?.let { model ->
                binding.edTitle.setText(model.title)
                binding.edDesc.setText(model.desc)
                binding.root.setBackgroundColor(model.color) // Используем сохранённое значение цвета
            }
        }
    }

    private fun setUpListeners() = with(binding) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                edsState()
            }

            private fun edsState() {
                if (edDesc.text.isNotEmpty() && edTitle.text.isNotEmpty()) {
                    tvReady.visibility = View.VISIBLE
                } else {
                    tvReady.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                edsState()
            }
        }

        edDesc.addTextChangedListener(textWatcher)
        edTitle.addTextChangedListener(textWatcher)

        tvReady.setOnClickListener {
            val title = edTitle.text.toString()
            val desc = edDesc.text.toString()
            val time = getCurrentDateTime()
            if (noteId != -1) {
                val updateNote = NoteModel(title, desc, time, color)
                updateNote.id = noteId
                App.appDataBase?.noteDao()?.updateNote(updateNote)
            } else {
                App.appDataBase?.noteDao()?.insertNote(NoteModel(title, desc, time, color))
            }
            findNavController().navigateUp()
        }
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        igColors.setOnClickListener {
            showColors()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showColors() {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_layout, null)

        val buttons = mapOf(
            R.id.orange to R.color.orange,
            R.id.red to R.color.red,
            R.id.green to R.color.green,
            R.id.blue to R.color.blue,
            R.id.purple to R.color.purple,
            R.id.pink to R.color.pink
        )

        buttons.forEach { (buttonId, colorId) ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                color = resources.getColor(colorId, null)
                binding.root.setBackgroundColor(color)
            }
        }

        builder.setView(view)
        val alertDialog = builder.create()

        alertDialog.window?.apply {
            attributes.gravity = Gravity.TOP or Gravity.END
        }
        alertDialog.show()
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("dd MMMM HH:mm ", Locale.getDefault())
        return dateFormat.format(System.currentTimeMillis())
    }

    private fun startUpdatingTime() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // Update UI on Main thread
                activity?.runOnUiThread {
                    val currentTime = getCurrentDateTime()
                    // Update your TextView with the new time here (assuming you have a TextView)
                    binding.tvDateTime.text = currentTime
                }
            }
        }, 0, 60000) // Update every 60 seconds (1 minute)
    }
}