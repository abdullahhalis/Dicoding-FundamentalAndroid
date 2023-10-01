package com.dicoding.mynotesapp.helper

import android.database.Cursor
import com.dicoding.mynotesapp.db.DataBaseContract
import com.dicoding.mynotesapp.entity.Note

object MappingHelper {
    fun mapToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        val noteList = ArrayList<Note>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DataBaseContract.NoteColums._ID))
                val title = getString(getColumnIndexOrThrow(DataBaseContract.NoteColums.TITLE))
                val description = getString(getColumnIndexOrThrow(DataBaseContract.NoteColums.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DataBaseContract.NoteColums.DATE))
                noteList.add(Note(id, title, description, date))
            }
        }
        return noteList
    }
}