package com.example.composenotesapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT*FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
    
}