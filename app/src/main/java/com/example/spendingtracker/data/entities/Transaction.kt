package com.example.spendingtracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val description: String,
    val amount: Int,
    val merchantId: Int,
    val tagId: Int,
    val transactionTime: Long,
)
