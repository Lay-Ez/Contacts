package com.example.contacts.editcontactscreen.ui.viewmodel

import android.annotation.SuppressLint
import com.example.contacts.base.model.Contact
import com.example.contacts.base.room.ContactsDao
import com.example.contacts.editcontactscreen.ui.DataEvent
import com.example.contacts.editcontactscreen.ui.Status
import com.example.contacts.editcontactscreen.ui.ViewState
import com.example.contacts.editcontactscreen.ui.viewmodel.base.ContactViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditContactViewModel(private val contactsDao: ContactsDao, contactId: Int) :
    ContactViewModel() {

    init {
        contactsDao.getContactById(contactId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ contact ->
                viewState.value =
                    ViewState(
                        Status.CONTENT,
                        contact
                    )
            }, {
                processDataEvent(
                    DataEvent.ErrorLoadingContact(
                        it
                    )
                )
            })
    }

    @SuppressLint("CheckResult")
    override fun saveContact(contact: Contact) {
        contactsDao.updateContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                processDataEvent(DataEvent.ContactSaved)
            }, {
                processDataEvent(
                    DataEvent.ErrorUpdatingContact(
                        it
                    )
                )
            })
    }

    @SuppressLint("CheckResult")
    override fun deleteContact(contact: Contact) {
        contactsDao.deleteContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                processDataEvent(DataEvent.ContactSaved)
            }, {
                processDataEvent(
                    DataEvent.ErrorUpdatingContact(
                        it
                    )
                )
            })
    }
}