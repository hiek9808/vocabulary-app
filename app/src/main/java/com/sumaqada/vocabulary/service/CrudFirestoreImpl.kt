package com.sumaqada.vocabulary.service

import kotlinx.coroutines.flow.Flow

abstract class CrudFirestoreImpl<T>(
    private val firestore: String,
    private val auth: String
) : CrudFirestore<T> {

    override suspend fun insert(t: T): T {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<T>> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(wordId: String): T? {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: T) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(wordId: String) {
        TODO("Not yet implemented")
    }
}