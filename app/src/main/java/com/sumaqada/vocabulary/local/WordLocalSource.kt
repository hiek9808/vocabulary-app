package com.sumaqada.vocabulary.local

import com.sumaqada.vocabulary.ui.home.WordHomeUI
import kotlinx.coroutines.flow.Flow

interface WordLocalSource<T> {

    suspend fun save(word: T)
    suspend fun saveAll(vararg words: T)
    fun getById(wordId: Int): Flow<T>
    fun getAll(): Flow<List<T>>
    suspend fun update(word: T)
    suspend fun updateAll(vararg words: T)
    suspend fun delete(word: T)
    suspend fun deleteAll(vararg words: T)
    suspend fun deleteTable()
    fun getAllNoSynchronized(): Flow<List<T>>
    fun getAllWordHomeUI(): Flow<List<WordHomeUI>>
}