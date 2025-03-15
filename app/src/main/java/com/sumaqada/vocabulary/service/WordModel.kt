package com.sumaqada.vocabulary.service

import com.google.firebase.firestore.DocumentId
import java.util.Date

sealed interface Model

data class WordModel(
    @DocumentId
    val id: String = "",
    val word: String = "",
    val translated: String = "",
    val description: String = "",
    val isAvailable: Boolean = true,
    val createdAt: Date = Date(),
    val userId: String = ""
) : Model
