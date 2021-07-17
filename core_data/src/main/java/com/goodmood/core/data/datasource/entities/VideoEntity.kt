package com.goodmood.core.data.datasource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = Random().nextLong(),
    val timestamp: Long,
    val path: String
)