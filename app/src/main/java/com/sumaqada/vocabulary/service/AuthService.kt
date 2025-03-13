package com.sumaqada.vocabulary.service

import android.content.Context
import com.sumaqada.vocabulary.repository.UserData


interface AuthService {

    suspend fun singIn(context: Context): UserData?

    suspend fun singOut()

    suspend fun getUserData(): UserData?
}