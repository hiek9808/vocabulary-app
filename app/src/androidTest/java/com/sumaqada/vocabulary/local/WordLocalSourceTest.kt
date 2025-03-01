package com.sumaqada.vocabulary.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sumaqada.vocabulary.TestUtil
import com.sumaqada.vocabulary.data.VocabularyDatabase
import com.sumaqada.vocabulary.data.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class WordLocalSourceTest {

    private lateinit var wordLocal: WordLocalSourceImpl
    private lateinit var db: VocabularyDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, VocabularyDatabase::class.java).build()
        wordLocal = WordLocalSourceImpl(db.wordDao(), Dispatchers.IO)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeLocalWordAndRead() {

        val word = TestUtil.wordEntity

        runBlocking {
            wordLocal.save(word)
        }
        val wordInserted: WordEntity
        runBlocking {
            wordInserted = wordLocal.getAll().first()[0]
        }
        assert(TestUtil.compareWordEntity(word, wordInserted))

    }

    @Test
    @Throws(IOException::class)
    fun updateLocalWordAndRead() {
        val word = TestUtil.wordEntity

        val wordInserted: WordEntity
        runBlocking {
            wordLocal.save(word)
            wordInserted = wordLocal.getAll().first()[0]
        }

        val wordToUpdate = wordInserted.copy(word = "English", translated = "Ingles")
        val wordUpdated: WordEntity
        runBlocking {
            wordLocal.update(wordToUpdate)
            wordUpdated = wordLocal.getAll().first()[0]
        }

        assert(wordToUpdate == wordUpdated)


    }

    @Test
    @Throws(IOException::class)
    fun writeLocalWordsAndRead() {
        val words = TestUtil.words

        val wordsInserted: List<WordEntity>
        runBlocking {
            wordLocal.saveAll(*words.toTypedArray())
            wordsInserted = wordLocal.getAll().first()
        }

        assert(words.size == wordsInserted.size)
    }
}
