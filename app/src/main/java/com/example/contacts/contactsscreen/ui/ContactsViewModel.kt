package com.example.contacts.contactsscreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.base.model.Contact
import com.example.contacts.base.room.ContactsDao

class ContactsViewModel(private val contactsDao: ContactsDao) : ViewModel() {
    val contacts: LiveData<List<Contact>> = contactsDao.getAllContacts()
}