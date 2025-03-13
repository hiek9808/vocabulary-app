package com.sumaqada.vocabulary.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val userData: Flow<UserData?>

    suspend fun loadUserData()

    suspend fun singInWithGoogle(context: Context)

    suspend fun signOut()
}