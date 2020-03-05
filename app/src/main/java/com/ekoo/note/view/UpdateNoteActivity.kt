package com.ekoo.note.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ekoo.note.*
import kotlinx.android.synthetic.main.activity_update_note.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class UpdateNoteActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        progress_view.hide()
        viewModel = obtainViewModel(this)
        viewModel.updateNoteStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == true) {
                finish()
            } else {
                toast("error")
                progress_view.hide()
            }
        })

        val id = intent.getStringExtra(NOTE_ID)
        val noteData = intent.getParcelableExtra<NoteModel>(
            NOTE_DATA
        )

        if (id != null && noteData != null) {

            val formatter = SimpleDateFormat("hh:mm dd-MMMM", Locale.getDefault())
            val date = formatter.format(noteData.timestamp!!)

            timestamp_UpdateNote.text = date
            note_EditText_UpdateNote.setText(noteData.data)

            delete_button_UpdateNote.setOnClickListener {
                progress_view.show()
                viewModel.deleteNote(id)
            }

            save_button_UpdateNote.setOnClickListener {
                val newData = note_EditText_UpdateNote.text
                if (newData.isNullOrEmpty()) {
                    toast("Data Cannot Null")
                } else {
                    progress_view.show()
                    viewModel.updateNote(newData.toString(), id)
                }
            }
        }

    }


    companion object {
        const val NOTE_ID = "id"
        const val NOTE_DATA = "note"
    }
}
