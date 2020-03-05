package com.ekoo.note.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekoo.note.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        empty_data_view.hide()
        setSupportActionBar(home_toolbar)
        viewModel = obtainViewModel(this)

        viewModel.signOutStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == true) {
                startActivity<LoginActivity>()
                finishAffinity()
            } else {
                toast("error")
            }
        })

        add_button.setOnClickListener {
            startActivity<AddNotesActivity>()
        }

        val query = viewModel.noteReference
        if (query != null) {
            val recyclerOptions = FirestoreRecyclerOptions.Builder<NoteModel>()
                .setQuery(query, NoteModel::class.java)
                .setLifecycleOwner(this)
                .build()

            val noteAdapter = NoteAdapter(recyclerOptions)

            noteAdapter.onItemClick = {
                val (id, data) = it
                startActivity<UpdateNoteActivity>(
                    UpdateNoteActivity.NOTE_ID to id,
                    UpdateNoteActivity.NOTE_DATA to data
                )
            }

            noteAdapter.isEmptyItem.observe(this, Observer { isEmpty ->
                if (isEmpty == true) {
                    empty_data_view.show()
                } else {
                    empty_data_view.hide()
                }
            })

            note_list_RecyclerView.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = noteAdapter
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out_button -> {
                val array = arrayOf("Log Out", "Revoke Access")
                val builder = AlertDialog.Builder(this)

                builder.setItems(array) { _, option ->
                    if (option == 0) viewModel.signOut() else viewModel.revokeAccess()
                }

                val dialog = builder.create()
                dialog.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
