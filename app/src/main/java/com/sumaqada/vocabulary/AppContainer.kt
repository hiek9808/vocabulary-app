package com.sumaqada.vocabulary

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.sumaqada.vocabulary.data.VocabularyDatabase
import com.sumaqada.vocabulary.data.WordEntity
import com.sumaqada.vocabulary.local.WordLocalSource
import com.sumaqada.vocabulary.local.WordLocalSourceImpl
import com.sumaqada.vocabulary.remote.AuthRemoteSource
import com.sumaqada.vocabulary.remote.AuthRemoteSourceImpl
import com.sumaqada.vocabulary.remote.WordRemoteSource
import com.sumaqada.vocabulary.remote.WordRemoteSourceImpl
import com.sumaqada.vocabulary.repository.AuthRepository
import com.sumaqada.vocabulary.repository.AuthRepositoryImpl
import com.sumaqada.vocabulary.repository.WordRepository
import com.sumaqada.vocabulary.repository.WordRepositoryImpl
import com.sumaqada.vocabulary.service.AuthGoogleServiceImpl
import com.sumaqada.vocabulary.service.AuthService
import com.sumaqada.vocabulary.service.WordService
import com.sumaqada.vocabulary.service.WordFirestoreServiceImpl
import kotlinx.coroutines.Dispatchers

interface AppContainer {
    val wordService: WordService

    val wordLocalSource: WordLocalSource<WordEntity>
    val wordRemoteSource: WordRemoteSource
    val wordRepository: WordRepository

    val authService: AuthService
    val authRemoteSource: AuthRemoteSource
    val authRepository: AuthRepository

}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {


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

    private val authFirebase = Firebase.auth

    private val firestore = Firebase.firestore

    private val ioDispatcher = Dispatchers.IO

    override val wordService: WordService = WordFirestoreServiceImpl(firestore)

    override val wordLocalSource: WordLocalSource<WordEntity> =
        WordLocalSourceImpl(getVocabularyDb(context).wordDao(), ioDispatcher)

    override val wordRemoteSource: WordRemoteSource = WordRemoteSourceImpl(wordService, ioDispatcher)

    override val authService: AuthService = AuthGoogleServiceImpl(context, authFirebase)

    override val authRemoteSource: AuthRemoteSource = AuthRemoteSourceImpl()

    override val authRepository: AuthRepository = AuthRepositoryImpl(authService)

    override val wordRepository: WordRepository =
        WordRepositoryImpl(wordLocalSource, wordRemoteSource, authRepository)
}
