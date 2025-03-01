package com.sumaqada.vocabulary

import android.content.Context
import androidx.room.Room
import com.sumaqada.vocabulary.data.VocabularyDatabase
import com.sumaqada.vocabulary.data.WordDao
import com.sumaqada.vocabulary.repository.WordRepository
import com.sumaqada.vocabulary.repository.WordRepositoryImpl

interface AppContainer {
    fun getWordRepository(context: Context): WordRepository
}

class DefaultAppContainer : AppContainer {


    private fun getVocabularyDb(context: Context): VocabularyDatabase {

        return Room
            .databaseBuilder(
                context = context,
                klass = VocabularyDatabase::class.java,
                name = "vocabulary_db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun getWordDao(context: Context): WordDao = getVocabularyDb(context).wordDao()

    override fun getWordRepository(context: Context): WordRepository {
        return WordRepositoryImpl(getWordDao(context))
    }
}
