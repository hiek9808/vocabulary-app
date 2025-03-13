package com.sumaqada.vocabulary.remote

import com.sumaqada.vocabulary.service.WordModel
import kotlinx.coroutines.flow.Flow

interface WordRemoteSource {

    suspend fun insert(word: WordModel): WordModel
    suspend fun insertAll(words: List<WordModel>): List<WordModel>
    fun getAll(userId: String): Flow<List<WordModel>>
    suspend fun getById(wordId: String): WordModel?
    suspend fun update(word: WordModel)
    suspend fun delete(wordId: String)

}