package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.data.WordDao
import com.sumaqada.vocabulary.data.WordEntity

class WordRepositoryImpl(
    private val wordDao: WordDao
) : WordRepository {

    override suspend fun insertWord(wordEntity: WordEntity) {
        wordDao.insert(wordEntity)
    }
}