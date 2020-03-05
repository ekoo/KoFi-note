package com.ekoo.note

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class Repository(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val googleSignInClient: GoogleSignInClient
) {

    val currentUser = auth.currentUser
    val signInIntent = googleSignInClient.signInIntent

    val signInStatus = LiveEvent<Boolean>()
    suspend fun signIn(data: Intent?) {
        try {
            val signInAccount = GoogleSignIn.getSignedInAccountFromIntent(data).await()
            val credential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
            auth.signInWithCredential(credential).await()
            signInStatus.postValue(true)
        } catch (e: Exception) {
            signInStatus.postValue(false)
        }
    }

    val signOutStatus = LiveEvent<Boolean>()
    suspend fun signOut() {
        try {
            auth.signOut()
            googleSignInClient.signOut().await()
            signOutStatus.postValue(true)
        } catch (e: Exception) {
            signOutStatus.postValue(false)
        }
    }

    suspend fun revokeAccess() {
        try {
            auth.signOut()
            googleSignInClient.revokeAccess().await()
            signOutStatus.postValue(true)
        } catch (e: Exception) {
            signOutStatus.postValue(false)
        }
    }

    fun noteReference(): CollectionReference? {
        val user = auth.currentUser
        return if (user != null) db.collection(user.uid) else null
    }

    val addNoteStatus = LiveEvent<Boolean>()
    suspend fun addNote(noteData: String) {
        try {
            val user = auth.currentUser
            if (user != null) {
                db.collection(user.uid).add(NoteModel(null, noteData)).await()
                addNoteStatus.postValue(true)
            }
        } catch (e: Exception) {
            addNoteStatus.postValue(false)
        }
    }

    val updateNoteStatus = LiveEvent<Boolean>()
    suspend fun updateNote(newNoteData: String, noteID: String) {
        try {
            val user = auth.currentUser
            if (user != null) {
                db.collection(user.uid).document(noteID).set(NoteModel(null, newNoteData)).await()
                updateNoteStatus.postValue(true)
            }
        } catch (e: Exception) {
            updateNoteStatus.postValue(false)
        }
    }

    suspend fun deleteNote(noteId: String) {
        try {
            val user = auth.currentUser
            if (user != null) {
                db.collection(user.uid).document(noteId).delete().await()
                updateNoteStatus.postValue(true)
            }
        } catch (e: Exception) {
            updateNoteStatus.postValue(false)
        }
    }

}