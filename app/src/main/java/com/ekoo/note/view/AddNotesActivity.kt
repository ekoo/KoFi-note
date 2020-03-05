package com.ekoo.note.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ekoo.note.*
import kotlinx.android.synthetic.main.activity_add_notes.*
import org.jetbrains.anko.toast

class AddNotesActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        progress_view.hide()
        viewModel = obtainViewModel(this)
        viewModel.addNoteStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == true) {
                toast("Note Successfully Added")
                finish()
            } else {
                progress_view.hide()
                toast("Error")
            }
        })

        save_button_AddNote.setOnClickListener {
            val noteData = note_EditText_AddNote.text
            if (noteData.isNullOrEmpty()) {
                toast("Can't Add Empty Note")
            } else {
                progress_view.show()
                viewModel.addNote(noteData.toString())
            }
        }
    }
}
