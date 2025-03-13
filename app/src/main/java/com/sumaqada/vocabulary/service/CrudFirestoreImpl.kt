package com.sumaqada.vocabulary.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

private const val TAG = "CrudFirestoreImpl"

abstract class CrudFirestoreImpl<T: Model>(
    open val firestore: FirebaseFirestore,
    open val pathCollection: String
) : CrudFirestore<T> {

    override suspend fun insert(t: T): T {
        Log.i(TAG, "insert: abstract")
        val wordId = firestore
            .collection(pathCollection)
            .add(t)
            .await()
            .id
        return t.setId(wordId)
    }

    inline fun <reified T : Model> getAllModel(userId: String): Flow<List<T>> {
        return firestore
            .collection(pathCollection)
            .whereEqualTo("userId", userId)
            .whereEqualTo("available", true)
            .dataObjects<T>()
    }

    suspend inline fun <reified T : Model> getModelById(wordId: String): T? {
        return firestore
            .collection(pathCollection)
            .document(wordId)
            .get()
            .await()
            .toObject(T::class.java)
    }

    override suspend fun update(t: T) {
        firestore
            .collection(pathCollection)
            .document(t.getId())
            .set(t)
            .await()
    }

    override suspend fun delete(wordId: String) {
        firestore
            .collection(pathCollection)
            .document(wordId)
            .delete()
            .await()
    }
}

fun <T : Model> T.getId(): String {
    return when(this) {
        is WordModel -> this.id
        else -> throw Exception("No class accepted")
    }
}

fun <T : Model> T.setId(id: String): T {
    return when(this) {
        is WordModel -> this.copy(id = id) as T
        else -> throw Exception("No class accepted")
    }
}