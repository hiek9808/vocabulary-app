package com.sumaqada.vocabulary.service

import android.service.autofill.UserData
import kotlinx.coroutines.flow.Flow

interface AuthService {

    suspend fun singIn()

    suspend fun singOut()

    fun getSignedInUser(): Flow<UserData?>
}