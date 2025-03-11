package com.sumaqada.vocabulary.service

import java.util.Date

data class WordModel(
    val id: String = "",
    val word: String = "",
    val translated: String = "",
    val description: String = "",
    val isAvailable: Boolean = true,
    val createdAt: Date = Date(),
    val userId: String = ""
)
