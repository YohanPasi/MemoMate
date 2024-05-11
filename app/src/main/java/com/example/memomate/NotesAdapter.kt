package com.example.memomate

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, private val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db:NotesDatabaseHelper = NotesDatabaseHelper(context)
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView? = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView? = itemView.findViewById(R.id.contentTextView)
        val editButton: ImageView? = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView? = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.titleTextView?.text = currentNote.title
        holder.contentTextView?.text = currentNote.content

        holder.editButton?.setOnClickListener {
            val intent = Intent(context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", currentNote.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton?.setOnClickListener {
            db.deleteNote(currentNote.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note deleted",Toast.LENGTH_SHORT).show()
        }

    }


    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
