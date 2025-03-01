package com.sumaqada.vocabulary.repository

import com.sumaqada.vocabulary.data.WordEntity

interface WordRepository {

    suspend fun insertWord(wordEntity: WordEntity)
}