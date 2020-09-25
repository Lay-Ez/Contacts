package com.example.contacts

import androidx.room.Room
import com.example.contacts.base.room.ContactsDao
import com.example.contacts.base.room.ContactsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<ContactsDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ContactsDatabase::class.java,
            "contactdb"
        ).build()
    }

    single<ContactsDao> {
        get<ContactsDatabase>().contactsDao()
    }
}