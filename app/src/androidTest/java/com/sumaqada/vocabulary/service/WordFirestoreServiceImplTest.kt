package com.sumaqada.vocabulary.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.sumaqada.vocabulary.TestUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class WordFirestoreServiceImplTest {

    private lateinit var wordFirestoreService: WordFirestoreServiceImpl
    private lateinit var firestore: FirebaseFirestore

    @Before
    fun createService() {
        val context: Context = ApplicationProvider.getApplicationContext()
        firestore = Firebase.firestore

        wordFirestoreService = WordFirestoreServiceImpl(firestore = firestore)
    }

    @After
    @Throws(IOException::class)
    fun clearDb() {
        runTest {
            firestore.collection("word")
                .dataObjects<WordModel>()
                .first()
                .forEach {
                    firestore.collection("word")
                        .document(it.id)
                        .delete()
                        .await()
                }
        }
    }

    @Test
    @Throws(IOException::class)
    fun insert() = runTest {
        val wordModel = TestUtil.WordModel
        val wordInserted = wordFirestoreService.insert(wordModel)

        wordFirestoreService.getById(wordInserted.id).let { word ->
            assert(word != null)
            assert(word == wordInserted)
        }

    }
}