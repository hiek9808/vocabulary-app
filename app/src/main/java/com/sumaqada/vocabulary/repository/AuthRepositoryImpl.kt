package com.sumaqada.vocabulary.repository


import android.content.Context
import com.sumaqada.vocabulary.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {

    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    override val userData: StateFlow<UserData?>
        get() = _userData.asStateFlow()

    override suspend fun loadUserData() {
        _userData.emit(authService.getUserData())
    }

    override suspend fun singInWithGoogle(context: Context) {
        val user = authService.singIn(context)
        _userData.emit(user)
    }

    override suspend fun signOut() {
        authService.singOut()
        _userData.emit(null)
    }
}