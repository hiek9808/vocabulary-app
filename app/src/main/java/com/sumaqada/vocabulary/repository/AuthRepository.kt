package com.sumaqada.vocabulary.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val userData: Flow<String?>
}