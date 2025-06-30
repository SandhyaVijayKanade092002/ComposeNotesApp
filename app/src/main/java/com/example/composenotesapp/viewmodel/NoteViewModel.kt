package com.example.composenotesapp.viewmodel

import android.util.Log

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenotesapp.model.NoteDatabase
import com.example.composenotesapp.model.NoteEntity
import com.example.composenotesapp.model.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val notes: StateFlow<List<NoteEntity>>

    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)

        // Convert Flow to StateFlow
        notes = repository.allNotes
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            Log.d("NOTE_DEBUG", "Trying to insert: $title | $content")
            repository.insert(NoteEntity(title = title, content = content))
        }
    }


    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
}
