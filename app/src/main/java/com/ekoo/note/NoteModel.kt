package com.ekoo.note

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NoteModel(
    @ServerTimestamp
    val timestamp: Date? = null,
    val data: String? = null
) : Parcelable