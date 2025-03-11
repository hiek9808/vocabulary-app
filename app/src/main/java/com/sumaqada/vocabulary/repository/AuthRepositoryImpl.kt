package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.remote.AuthRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl(
    private val authRemoteSource: AuthRemoteSource
) : AuthRepository {
    override val userData: Flow<String?> = flowOf("Kervin middleName lastName")
}