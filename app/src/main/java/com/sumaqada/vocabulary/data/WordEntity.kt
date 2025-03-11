package com.sumaqada.vocabulary.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "Word")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String? = null,
    val word: String = "",
    val translated: String = "",
    val description: String = "",
    val createAt: Date = Date(),
    val isAvailable: Boolean = true,
    val synchronized: Boolean = false,
    val userId: String? = null
)
