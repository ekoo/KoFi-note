package com.ekoo.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import kotlinx.android.synthetic.main.notes_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(trashData: FirestoreRecyclerOptions<NoteModel>) :
    FirestoreRecyclerAdapter<NoteModel, NoteAdapter.ViewHolder>(trashData) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.time_TextView
        val data: TextView = view.data_textView
        val item = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notes_item,
                parent,
                false
            )
        )
    }

    var onItemClick: ((Pair<String, NoteModel>) -> Unit)? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: NoteModel) {

        val id = this.snapshots.getSnapshot(position).id
        val formatter = SimpleDateFormat("hh:mm dd-MMMM", Locale.getDefault())
        val date = formatter.format(item.timestamp!!)

        holder.time.text = date
        holder.data.text = item.data
        holder.item.setOnClickListener {
            onItemClick?.invoke(Pair(id, item))
        }
    }

    val isEmptyItem = MutableLiveData<Boolean>()
    override fun onDataChanged() {
        super.onDataChanged()
        if (itemCount == 0) {
            isEmptyItem.postValue(true)
        } else {
            isEmptyItem.postValue(false)
        }
    }

}