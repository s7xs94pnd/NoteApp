package com.example.noteapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteTable")
data class NoteModel(val title: String, val desc: String, val time: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
