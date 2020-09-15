package com.example.contacts.base.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: String = "",
    val imageUri: String,
    val firstName: String,
    val lastName: String
)