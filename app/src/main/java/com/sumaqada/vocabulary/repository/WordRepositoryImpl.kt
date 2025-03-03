package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.local.WordLocalSource
import kotlinx.coroutines.flow.Flow

class WordRepositoryImpl(
    private val wordLocalSource: WordLocalSource<WordEntity>
) : WordRepository {

    override suspend fun insertWord(wordEntity: WordEntity) {
        wordLocalSource.save(wordEntity)
    }

    override fun getWordById(wordId: Int): Flow<WordEntity> {
        return wordLocalSource.getById(wordId)
    }

    override fun getAllWords(): Flow<List<WordEntity>> {
        return wordLocalSource.getAll()
    }

    override suspend fun updateWord(word: WordEntity) {
        wordLocalSource.updateAll(word)
    }


    override suspend fun deleteWord(word: WordEntity) {
        wordLocalSource.delete(word)
    }
}