package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.ui.home.WordHomeUI
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun insertWord(wordEntity: WordEntity)

    fun getWordById(wordId: Int): Flow<WordEntity>

    fun getAllWords(): Flow<List<WordEntity>>

    suspend fun updateWord(word: WordEntity)

    suspend fun deleteWord(word: WordEntity)

    fun getAllNoSynchronized(): Flow<List<WordEntity>>

    suspend fun syncWordFromLocalToRemote(words: List<WordEntity>)
    suspend fun syncWordFromLocalToRemote()
    suspend fun syncWordFromRemoteToLocal()

    fun getAllWordHomeUI(): Flow<List<WordHomeUI>>
}