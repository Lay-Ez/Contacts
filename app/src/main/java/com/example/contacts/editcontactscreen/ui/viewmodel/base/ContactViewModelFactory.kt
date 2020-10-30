package com.example.contacts.editcontactscreen.ui.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.base.data.ImageSaver
import com.example.contacts.base.room.ContactsDao
import com.example.contacts.editcontactscreen.EditContactFragment
import com.example.contacts.editcontactscreen.ui.viewmodel.EditContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.NewContactViewModel

class ContactViewModelFactory(
    private val contactId: Int,
    private val contactsDao: ContactsDao,
    private val imageSaver: ImageSaver
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (contactId == EditContactFragment.NEW_CONTACT_ID && modelClass.isAssignableFrom(
                NewContactViewModel::class.java
            )
        ) {
            return NewContactViewModel(
                contactsDao,
                imageSaver
            ) as T
        } else if (contactId != EditContactFragment.NEW_CONTACT_ID && modelClass.isAssignableFrom(
                NewContactViewModel::class.java
            )
        ) {
            return EditContactViewModel(
                contactsDao,
                contactId,
                imageSaver
            ) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}