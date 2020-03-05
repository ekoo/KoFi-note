@file:Suppress("DEPRECATION")

package com.ekoo.note

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders

fun obtainViewModel(activity: FragmentActivity): NoteViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProviders.of(activity, factory).get(NoteViewModel::class.java)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
