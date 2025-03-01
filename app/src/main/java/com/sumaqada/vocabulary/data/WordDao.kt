package com.sumaqada.vocabulary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: WordEntity)

    @Insert
    suspend fun insertAll(vararg word: WordEntity)

    @Query("SELECT * FROM Word WHERE id = :id")
    fun getById(id: Int): Flow<WordEntity>

    @Query("SELECT * FROM Word")
    fun getByAll(): Flow<List<WordEntity>>

    @Update
    suspend fun update(word: WordEntity)

    @Update
    suspend fun updateAll(vararg words: WordEntity)

    @Delete
    suspend fun delete(word: WordEntity)

    @Delete
    suspend fun deleteAll(vararg words: WordEntity)

    @Query("DELETE FROM Word")
    suspend fun deleteTable()



}