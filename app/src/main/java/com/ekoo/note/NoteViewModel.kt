package com.ekoo.note

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: Repository) : ViewModel() {

    val currentUser = repository.currentUser
    val signInIntent = repository.signInIntent

    val signInStatus = repository.signInStatus
    fun signIn(data: Intent?) = viewModelScope.launch(Dispatchers.IO) {
        repository.signIn(data)
    }

    val signOutStatus = repository.signOutStatus
    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        repository.signOut()
    }

    fun revokeAccess() = viewModelScope.launch(Dispatchers.IO) {
        repository.revokeAccess()
    }

    val noteReference = repository.noteReference()

    val addNoteStatus = repository.addNoteStatus
    fun addNote(noteData: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNote(noteData)
    }

    val updateNoteStatus = repository.updateNoteStatus
    fun updateNote(newNoteData: String, noteID: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(newNoteData, noteID)
    }

    fun deleteNote(noteId: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(noteId)
    }

}