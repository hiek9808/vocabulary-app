package com.sumaqada.vocabulary.remote

import android.util.Log
import com.sumaqada.vocabulary.service.WordService
import com.sumaqada.vocabulary.service.WordModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val TAG = "WordRemoteSourceImpl"


class WordRemoteSourceImpl(
    private val wordFirestoreService: WordService,
    private val ioDispatcher: CoroutineDispatcher
) : WordRemoteSource {

    override suspend fun insert(word: WordModel): WordModel {
        return withContext(ioDispatcher) {
            wordFirestoreService.insert(word)
        }
    }

    override suspend fun insertAll(words: List<WordModel>): List<WordModel> {
        return withContext(ioDispatcher) {
            words.map {
                val id = wordFirestoreService.insert(it).id
                it.copy(id = id)
            }
        }
    }

    override fun getAll(userId: String): Flow<List<WordModel>> = wordFirestoreService.getAll(userId)

    override suspend fun getById(wordId: String): WordModel? {
        return withContext(ioDispatcher) {
            wordFirestoreService.getById(wordId)
        }
    }

    override suspend fun update(word: WordModel) {
        withContext(ioDispatcher) {
            wordFirestoreService.update(word)
        }
    }

    override suspend fun delete(wordId: String) {
        withContext(ioDispatcher) {
            wordFirestoreService.delete(wordId)
        }
    }
}