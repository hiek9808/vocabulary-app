package com.sumaqada.vocabulary.service

import kotlinx.coroutines.flow.Flow

interface CrudFirestore<T> {

    suspend fun insert(t: T): T
    fun getAll(userId: String): Flow<List<T>>
    suspend fun getById(wordId: String): T?
    suspend fun update(t: T)
    suspend fun delete(wordId: String)
}