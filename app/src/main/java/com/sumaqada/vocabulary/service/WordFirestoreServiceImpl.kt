package com.sumaqada.vocabulary.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow


class WordFirestoreServiceImpl(
    override val firestore: FirebaseFirestore,
    override val pathCollection: String = "word",
) : WordService, CrudFirestoreImpl<WordModel>(firestore, pathCollection) {

    override fun getAll(userId: String): Flow<List<WordModel>> = getAllModel(userId)

    override suspend fun getById(wordId: String): WordModel? = getModelById(wordId)
}