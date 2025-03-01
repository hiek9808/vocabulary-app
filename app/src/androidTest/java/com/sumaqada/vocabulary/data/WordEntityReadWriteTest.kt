package com.sumaqada.vocabulary.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class WordEntityReadWriteTest {

    private lateinit var wordDao: WordDao
    private lateinit var db: VocabularyDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, VocabularyDatabase::class.java).build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeWordAndRead() {

        val word = WordEntity(word = "Name", translated = "Nombre")

        runBlocking {
            wordDao.insert(word)
        }

        assert(word.id == 0)

    }

    
}