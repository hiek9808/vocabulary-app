package com.sumaqada.vocabulary

import android.content.Context
import androidx.room.Room
import com.sumaqada.vocabulary.data.VocabularyDatabase
import com.sumaqada.vocabulary.data.WordDao
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.local.WordLocalSource
import com.sumaqada.vocabulary.local.WordLocalSourceImpl
import com.sumaqada.vocabulary.repository.WordRepository
import com.sumaqada.vocabulary.repository.WordRepositoryImpl
import kotlinx.coroutines.Dispatchers

interface AppContainer {
    fun getWordRepository(context: Context): WordRepository
    fun getWordLocalSource(context: Context): WordLocalSource<WordEntity>
}

class DefaultAppContainer : AppContainer {


    companion object {
        private var instanceDb: VocabularyDatabase? = null
        private fun getVocabularyDb(context: Context): VocabularyDatabase {

            return instanceDb ?: synchronized(this) {
                instanceDb ?: Room
                    .databaseBuilder(
                        context = context,
                        klass = VocabularyDatabase::class.java,
                        name = "vocabulary_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }.also { instanceDb = it }

        }
    }

    private val ioDispatcher = Dispatchers.IO



    private fun getWordDao(context: Context): WordDao = getVocabularyDb(context).wordDao()

    override fun getWordRepository(context: Context): WordRepository {
        return WordRepositoryImpl(getWordLocalSource(context))
    }

    override fun getWordLocalSource(context: Context): WordLocalSource<WordEntity> {
        return WordLocalSourceImpl(getWordDao(context), ioDispatcher)
    }
}
