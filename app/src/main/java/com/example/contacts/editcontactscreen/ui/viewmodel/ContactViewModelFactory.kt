package com.example.contacts.editcontactscreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.base.room.ContactsDao
import com.example.contacts.editcontactscreen.EditContactFragment
import com.example.contacts.editcontactscreen.ui.NewContactViewModel

class ContactViewModelFactory(
    private val contactId: Int,
    private val contactsDao: ContactsDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (contactId == EditContactFragment.NEW_CONTACT_ID && modelClass.isAssignableFrom(
                NewContactViewModel::class.java
            )
        ) {
            return NewContactViewModel(contactsDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}