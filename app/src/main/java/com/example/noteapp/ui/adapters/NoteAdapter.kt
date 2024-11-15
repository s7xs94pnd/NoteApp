package com.example.noteapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.ui.interfaces.OnClickItem

class NoteAdapter(private val LongClickItem: OnClickItem, private val ClickItem: OnClickItem) :
    ListAdapter<NoteModel, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NoteModel) = with(binding) {
            tvTitle.text = item.title + ":"
            tvDesc.text = item.desc
            tvTime.text = item.time
            root.setBackgroundColor(item.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnLongClickListener {
            LongClickItem.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener {
            ClickItem.onClick(getItem(position))
            true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }
    }
}