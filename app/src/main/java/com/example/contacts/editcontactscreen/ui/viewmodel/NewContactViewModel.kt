package com.example.contacts.editcontactscreen.ui.viewmodel

import android.annotation.SuppressLint
import com.example.contacts.base.data.ImageSaver
import com.example.contacts.base.model.Contact
import com.example.contacts.base.room.ContactsDao
import com.example.contacts.editcontactscreen.ui.DataEvent
import com.example.contacts.editcontactscreen.ui.viewmodel.base.ContactViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewContactViewModel(private val contactsDao: ContactsDao, imageSaver: ImageSaver) :
    ContactViewModel(imageSaver) {

    @SuppressLint("CheckResult")
    override fun saveContact(contact: Contact) {
        contactsDao.insertContact(contact)
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