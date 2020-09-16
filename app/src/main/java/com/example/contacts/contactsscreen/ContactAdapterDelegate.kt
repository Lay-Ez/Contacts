package com.example.contacts.contactsscreen

import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.base.model.Contact
import com.example.contacts.base.model.Item
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.contact_list_item.*

fun contactAdapterDelegate(): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<Contact, Item>(
        R.layout.contact_list_item
    ) {
        bind {
            Glide.with(containerView)
                .load(item.imageUri)
                .into(imageViewAvatar)
            textViewFirstName.text = item.firstName
            textViewLastName.text = item.lastName
        }
    }