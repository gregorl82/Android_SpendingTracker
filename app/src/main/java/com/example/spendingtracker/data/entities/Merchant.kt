package com.example.spendingtracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "merchants")
data class Merchant(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val merchantName: String,
)
