package com.example.contacts.editcontactscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.editcontactscreen.ui.NewContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_contact.*
import org.koin.android.ext.android.get


class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    companion object {
        const val NEW_CONTACT_ID = -1
        const val PICK_IMAGE_REQUEST_CODE = 42
        private const val TAG = "EditContactFragment"
    }

    private lateinit var viewModel: ContactViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment(arguments?.getInt("contact_id"))
        setupListeners()
        viewModel.viewState.observe(viewLifecycleOwner, Observer { displayViewState(it) })
    }

    private fun displayViewState(viewState: ViewState) {
        when (viewState.status) {
            Status.CONTENT -> {
                val contact = viewState.contact
                editTextFirstName.setText(contact.firstName)
                editTextLastName.setText(contact.lastName)
                Glide.with(this).load(contact.imageUri).into(imageViewAvatar)
            }
            Status.FINISHED -> {
                requireActivity().onBackPressed()
            }
            Status.ERROR -> {
                Snackbar.make(toolbar, R.string.generic_error_msg, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mnu_item_save -> viewModel.run {
                    processUiEvent(
                        UiEvent.OnNameUpdated(
                            editTextFirstName.text.toString(),
                            editTextLastName.text.toString()
                        )
                    )
                    processUiEvent(UiEvent.OnSaveClicked)
                }
                R.id.mnu_item_delete -> viewModel.processUiEvent(UiEvent.OnDeleteClicked)
            }
            return@setOnMenuItemClickListener true
        }
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        btnPickImage.setOnClickListener { onPickImageClicked() }
    }

    private fun onPickImageClicked() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(
            Intent.createChooser(intent, getString(R.string.image_picker_msg)),
            PICK_IMAGE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.processUiEvent(
                    UiEvent.OnNameUpdated(
                        editTextFirstName.text.toString(),
                        editTextLastName.text.toString()
                    )
                )
                viewModel.processUiEvent(UiEvent.OnImageUriUpdated(uri.toString()))
            }
        }
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