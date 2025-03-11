package com.sumaqada.vocabulary.remote

import android.service.autofill.UserData
import kotlinx.coroutines.flow.Flow

interface AuthRemoteSource {

    val userData: Flow<UserData?>

    suspend fun signInWithGoogle()

    suspend fun signOut()
}