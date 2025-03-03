package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.data.WordEntity
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun insertWord(wordEntity: WordEntity)

    fun getWordById(wordId: Int): Flow<WordEntity>

    fun getAllWords(): Flow<List<WordEntity>>

    suspend fun updateWord(word: WordEntity)

    suspend fun deleteWord(word: WordEntity)
}