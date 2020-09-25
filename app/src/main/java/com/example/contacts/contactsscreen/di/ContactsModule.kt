package com.example.contacts.contactsscreen.di

import com.example.contacts.contactsscreen.ui.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val contactsModule = module {

    viewModel {
        ContactsViewModel(get())
    }
}