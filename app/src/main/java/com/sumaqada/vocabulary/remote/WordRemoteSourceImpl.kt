package com.sumaqada.vocabulary.remote

import com.sumaqada.vocabulary.service.WordFirestoreService
import com.sumaqada.vocabulary.service.WordModel
import kotlinx.coroutines.flow.Flow

class WordRemoteSourceImpl(
    private val wordFirestoreService: WordFirestoreService
) : WordRemoteSource {

    override suspend fun insert(word: WordModel): WordModel {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(words: List<WordModel>): List<WordModel> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<WordModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(wordId: String): WordModel? {
        TODO("Not yet implemented")
    }

    override suspend fun update(word: WordModel) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(wordId: String) {
        TODO("Not yet implemented")
    }
}