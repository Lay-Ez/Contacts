package com.example.contacts.editcontactscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.R
import com.example.contacts.editcontactscreen.ui.NewContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.ContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.ContactViewModelFactory
import kotlinx.android.synthetic.main.fragment_edit_contact.*
import org.koin.android.ext.android.get


class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    companion object {
        const val NEW_CONTACT_ID = -1
        private const val TAG = "EditContactFragment"
    }

    private lateinit var viewModel: ContactViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment(arguments?.getInt("contact_id"))
    }

    private fun setupFragment(contactId: Int?) {
        if (contactId == null) {
            return
        }
        val factory = ContactViewModelFactory(contactId, get())
        viewModel = ViewModelProvider(this, factory).get(NewContactViewModel::class.java)
        if (contactId == NEW_CONTACT_ID) {
            setupNewContactUi()
        } else {
            setupEditContactUi()
        }
    }

    private fun setupNewContactUi() {
        toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.new_contact_menu)
            setTitle(R.string.new_contact_toolbar_title)
        }
    }

    private fun setupEditContactUi() {
        toolbar.apply {
            menu.clear()
            inflateMenu(R.menu.edit_contact_menu)
            setTitle(R.string.edit_contact_toolbar_name)
        }
    }
}