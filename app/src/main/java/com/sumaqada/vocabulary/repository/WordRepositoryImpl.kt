package com.sumaqada.vocabulary.repository


import android.util.Log
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.local.WordLocalSource
import com.sumaqada.vocabulary.remote.WordRemoteSource
import com.sumaqada.vocabulary.service.WordModel
import com.sumaqada.vocabulary.ui.home.WordHomeUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

private const val TAG = "WordRepositoryImpl"

class WordRepositoryImpl(
    private val wordLocalSource: WordLocalSource<WordEntity>,
    private val wordRemoteSource: WordRemoteSource,
    private val authRepository: AuthRepository
) : WordRepository {

    private val userData = authRepository.userData

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
        wordLocalSource.updateAll(word.copy(synchronized = false))
    }


    override suspend fun deleteWord(word: WordEntity) {
        wordLocalSource.update(word.copy(isAvailable = false, synchronized = false))
    }

    override fun getAllNoSynchronized(): Flow<List<WordEntity>> {
        return wordLocalSource.getAllNoSynchronized()
    }

    override suspend fun syncWordFromLocalToRemote(words: List<WordEntity>) {

        userData.first()?.userId?.let { userId ->
            words.filter { it.uid == null && it.userId == null }.forEach { word ->
                val remoteWordSynchronized = wordRemoteSource.insert(
                    word.toWordModel().copy(userId = userId)
                )
                wordLocalSource.update(remoteWordSynchronized.toWordEntity().copy(id = word.id))
            }

            words.filter { it.uid != null && it.userId != null }.forEach { word ->
                wordRemoteSource.update(word.toWordModel().copy(userId = userId))
                wordLocalSource.update(word.copy(synchronized = true))
            }
        } ?: throw NoUserFoundException()



    }

    override suspend fun syncWordFromLocalToRemote() {
        val allLocalWordsNoSynchronized = wordLocalSource.getAllNoSynchronized().first()
        syncWordFromLocalToRemote(allLocalWordsNoSynchronized)
    }

    override suspend fun syncWordFromRemoteToLocal() {
        userData.first()?.userId?.let { userId ->
            val allRemoteWordsSynchronized = wordRemoteSource.getAll(userId)
                .first()
            Log.i(TAG, "syncWordFromRemoteToLocal: allremotes: ${allRemoteWordsSynchronized.size}")
            wordLocalSource.deleteTable()
            Log.i(TAG, "syncWordFromRemoteToLocal: delete local Database")
            wordLocalSource.saveAll(*allRemoteWordsSynchronized.map { it.toWordEntity() }
                .toTypedArray())
            Log.i(TAG, "syncWordFromRemoteToLocal: insert to local database done")
        } ?: throw NoUserFoundException()

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

data class NoUserFoundException(
    val msg: String = "No user found"
) : Exception(msg)