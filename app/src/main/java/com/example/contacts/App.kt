package com.example.contacts

import android.app.Application
import com.example.contacts.contactsscreen.di.contactsModule
import com.example.contacts.editcontactscreen.di.editContactModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, contactsModule, editContactModule)
        }
    }
}