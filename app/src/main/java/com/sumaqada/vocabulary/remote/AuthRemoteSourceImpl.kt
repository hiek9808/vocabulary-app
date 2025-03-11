package com.sumaqada.vocabulary.remote

import android.service.autofill.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRemoteSourceImpl : AuthRemoteSource {

    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    override val userData: Flow<UserData?>
        get() = _userData

    override suspend fun signInWithGoogle() {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}