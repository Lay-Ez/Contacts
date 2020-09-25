package com.example.contacts.base.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.base.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}