package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.local.WordLocalSource
import com.sumaqada.vocabulary.remote.WordRemoteSource
import com.sumaqada.vocabulary.service.WordModel
import com.sumaqada.vocabulary.ui.home.WordHomeUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class WordRepositoryImpl(
    private val wordLocalSource: WordLocalSource<WordEntity>,
    private val wordRemoteSource: WordRemoteSource
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
        wordLocalSource.update(word.copy(isAvailable = false, synchronized = false))
    }

    override fun getAllNoSynchronized(): Flow<List<WordEntity>> {
        return wordLocalSource.getAllNoSynchronized()
    }

    override suspend fun syncWordFromLocalToRemote(words: List<WordEntity>) {

        words.filter { it.uid == null && it.userId == null }.forEach { word ->
            val remoteWordSynchronized = wordRemoteSource.insert(word.toWordModel())
            wordLocalSource.update(remoteWordSynchronized.toWordEntity().copy(id = word.id))
        }

        words.filter { it.uid != null && it.userId != null }.forEach { word ->
            wordRemoteSource.update(word.toWordModel())
            wordLocalSource.update(word.copy(synchronized = true))
        }
    }

    override suspend fun syncWordFromLocalToRemote() {
        val allLocalWordsNoSynchronized = wordLocalSource.getAllNoSynchronized().first()
        syncWordFromLocalToRemote(allLocalWordsNoSynchronized)
    }

    override suspend fun syncWordFromRemoteToLocal() {

        val allRemoteWordsSynchronized = wordRemoteSource.getAll()
            .filterNotNull()
            .first()

        wordLocalSource.deleteTable()
        wordLocalSource.saveAll(*allRemoteWordsSynchronized.map { it.toWordEntity() }.toTypedArray())
    }

    override fun getAllWordHomeUI(): Flow<List<WordHomeUI>> {
        return wordLocalSource.getAllWordHomeUI()
    }
}

fun WordEntity.toWordModel(): WordModel = WordModel(
    id = this.uid ?: "",
    word = this.word,
    translated = this.translated,
    description = this.description,
    isAvailable = this.isAvailable,
    createdAt = this.createAt,
    userId = this.userId ?: ""
)

fun WordModel.toWordEntity(): WordEntity = WordEntity(
    id = 0,
    uid = this.id,
    word = this.word,
    translated = this.translated,
    description = this.description,
    createAt = this.createdAt,
    isAvailable = this.isAvailable,
    synchronized = true,
    userId = this.userId
)