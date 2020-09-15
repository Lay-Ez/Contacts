package com.example.contacts.contactsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contacts.R
import kotlinx.android.synthetic.main.fragment_contacts.*


class ContactsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabNewContact.setOnClickListener { openNewContact() }
    }

    private fun openNewContact() {
        findNavController().navigate(R.id.action_contactsFragment_to_newContactFragment)
    }
}