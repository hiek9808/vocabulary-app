package com.sumaqada.vocabulary.local

import com.sumaqada.vocabulary.data.WordDao
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.ui.home.WordHomeUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WordLocalSourceImpl(
    private val wordDao: WordDao,
    private val ioDispatcher: CoroutineDispatcher
) : WordLocalSource<WordEntity> {


    override suspend fun save(word: WordEntity) {
        withContext(ioDispatcher) {
            wordDao.insert(word)
        }
    }

    override suspend fun saveAll(vararg words: WordEntity) {
        withContext(ioDispatcher) {
            wordDao.insertAll(*words)
        }
    }

    override fun getById(wordId: Int): Flow<WordEntity> = wordDao.getById(wordId)

    override fun getAll(): Flow<List<WordEntity>> = wordDao.getByAll()

    override suspend fun update(word: WordEntity) {
        withContext(ioDispatcher) {
            wordDao.update(word)
        }
    }

    override suspend fun updateAll(vararg words: WordEntity) {
        withContext(ioDispatcher) {
            wordDao.updateAll(*words)
        }
    }

    override suspend fun delete(word: WordEntity) {
        withContext(ioDispatcher) {
            wordDao.delete(word)
        }
    }

    override suspend fun deleteAll(vararg words: WordEntity) {
        withContext(ioDispatcher) {
            wordDao.deleteAll(*words)
        }
    }

    override suspend fun deleteTable() {
        withContext(ioDispatcher) {
            wordDao.deleteTable()
        }
    }

    override fun getAllNoSynchronized(): Flow<List<WordEntity>> {
        return  wordDao.getAllNoSynchronized()
    }

    override fun getAllWordHomeUI(): Flow<List<WordHomeUI>> {
        return wordDao.getAllWordHomeUI()
    }
}