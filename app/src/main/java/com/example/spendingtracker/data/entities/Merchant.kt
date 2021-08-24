package com.example.spendingtracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "merchants")
data class Merchant(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    val merchantName: String,
)
