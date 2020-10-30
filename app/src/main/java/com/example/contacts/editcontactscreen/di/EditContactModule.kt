package com.example.contacts.editcontactscreen.di

import com.example.contacts.base.data.ImageSaver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val editContactModule = module {

    single {
        ImageSaver(androidContext().contentResolver)
    }
}