package com.example.contacts.contactsscreen.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.base.model.Contact
import com.example.contacts.base.setAdapterAndCleanupOnDetachFromWindow
import com.example.contacts.base.setData
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private val viewModel: ContactsViewModel by viewModel()
    private val adapter = ListDelegationAdapter(contactAdapterDelegate {
        openEditContactWindow(it.id)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabNewContact.setOnClickListener { openNewContactWindow() }
        recyclerViewContacts.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewContacts.setAdapterAndCleanupOnDetachFromWindow(adapter)
        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            displayContacts(it)
        })
    }

    private fun displayContacts(contacts: List<Contact>) {
        adapter.setData(contacts)
    }

    private fun openNewContactWindow() {
        val action = ContactsFragmentDirections.actionToEditContact()
        findNavController().navigate(action)
    }

    private fun openEditContactWindow(contactId: Int) {
        val action = ContactsFragmentDirections.actionToEditContact(contactId)
        findNavController().navigate(action)
    }
}