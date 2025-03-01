package com.sumaqada.vocabulary.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Word")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String = "",
    val translated: String = "",
    val description: String = ""
)
