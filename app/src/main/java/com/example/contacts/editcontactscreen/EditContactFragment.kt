package com.example.contacts.editcontactscreen

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.editcontactscreen.ui.Status
import com.example.contacts.editcontactscreen.ui.UiEvent
import com.example.contacts.editcontactscreen.ui.ViewState
import com.example.contacts.editcontactscreen.ui.viewmodel.NewContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.base.ContactViewModel
import com.example.contacts.editcontactscreen.ui.viewmodel.base.ContactViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_contact.*
import org.koin.android.ext.android.get


class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    companion object {
        const val NEW_CONTACT_ID = -1
        const val PICK_IMAGE_REQUEST_CODE = 42
        const val TAKE_PICTURE_REQUEST_CODE = 43
        const val WRITE_STORAGE_REQUEST_CODE = 44
    }

    private lateinit var viewModel: ContactViewModel
    private val args: EditContactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment(args.contactId)
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
        btnTakePic.setOnClickListener { onTakePictureClicked() }
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

    private fun onTakePictureClicked() {
        if (!requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Snackbar.make(toolbar, R.string.error_no_camera_detected_msg, Snackbar.LENGTH_LONG)
                .show()
        } else {
            try {
                if (storagePermissionGranted()) {
                    openCamera()
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        WRITE_STORAGE_REQUEST_CODE
                    )
                }
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(toolbar, R.string.generic_error_msg, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun openCamera() {
        val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePicIntent, TAKE_PICTURE_REQUEST_CODE)
    }

    private fun storagePermissionGranted(): Boolean =
        (checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)


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
        } else if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            viewModel.processUiEvent(UiEvent.SaveImage(imageBitmap))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Snackbar.make(
                        toolbar,
                        R.string.external_storage_permission_denied,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupFragment(contactId: Int) {
        val factory =
            ContactViewModelFactory(
                contactId,
                get(),
                get()
            )
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