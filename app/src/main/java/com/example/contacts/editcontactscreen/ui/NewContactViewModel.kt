package com.example.contacts.editcontactscreen.ui

import android.annotation.SuppressLint
import com.example.contacts.base.model.Contact
import com.example.contacts.base.room.ContactsDao
import com.example.contacts.editcontactscreen.ui.viewmodel.ContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.DataEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewContactViewModel(private val contactsDao: ContactsDao) : ContactViewModel() {

    @SuppressLint("CheckResult")
    override fun saveContact(contact: Contact?) {
        if (contact == null) {
            processDataEvent(DataEvent.ErrorUpdatingContact(IllegalStateException("Cannot save null contact")))
        } else {
            contactsDao.insertContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    processDataEvent(DataEvent.ContactSaved)
                }, {
                    processDataEvent(DataEvent.ErrorUpdatingContact(it))
                })
        }
    }
}