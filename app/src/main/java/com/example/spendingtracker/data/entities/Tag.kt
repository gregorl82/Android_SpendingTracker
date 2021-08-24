package com.example.spendingtracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val tagName: String
)
