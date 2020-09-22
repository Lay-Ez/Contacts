package com.example.contacts.base.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contacts.base.model.Contact
import io.reactivex.Single

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id=:id")
    fun getContactById(id: Int): Single<Contact>

    @Insert
    fun insertContact(contact: Contact): Single<Unit>

    @Update
    fun updateContact(contact: Contact): Single<Unit>

    @Delete
    fun deleteContact(contact: Contact): Single<Unit>
}