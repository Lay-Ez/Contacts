package com.example.contacts.base.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contacts.base.model.Contact

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id=:id")
    fun getContactById(id: String): Contact

    @Insert
    fun insertContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)
}