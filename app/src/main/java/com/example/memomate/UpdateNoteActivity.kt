package com.example.memomate

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memomate.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            // Invalid note ID, finish the activity
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        if (note != null) {
            // Populate EditText fields with existing note data
            binding.updateTitleText.setText(note.title)
            binding.updateContentText.setText(note.content)
        }



        binding.updateBtn.setOnClickListener {
            // Get updated title and content from EditText fields
            val newTitle = binding.updateTitleText.text.toString()
            val newContent = binding.updateContentText.text.toString()

            // Create a new Note object with updated data
            val updatedNote = Note(noteId, newTitle, newContent)

            // Update the note in the database
            db.updateNote(updatedNote)

            // Finish the activity
            finish()

            // Show a toast message to indicate successful update
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()


        }
    }
}
