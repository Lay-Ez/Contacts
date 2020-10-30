package com.example.contacts.editcontactscreen.ui.viewmodel.base

import com.example.contacts.base.BaseViewModel
import com.example.contacts.base.Event
import com.example.contacts.base.data.ImageSaver
import com.example.contacts.base.model.Contact
import com.example.contacts.editcontactscreen.ui.DataEvent
import com.example.contacts.editcontactscreen.ui.Status
import com.example.contacts.editcontactscreen.ui.UiEvent
import com.example.contacts.editcontactscreen.ui.ViewState

abstract class ContactViewModel(private val imageSaver: ImageSaver) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState =
        ViewState(
            Status.CONTENT,
            Contact(
                imageUri = "",
                firstName = "",
                lastName = ""
            )
        )

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnSaveClicked -> {
                val contact = viewState.value?.contact
                return if (contact != null) {
                    saveContact(contact)
                    previousState.copy(
                        status = Status.PROCESSING
                    )
                } else {
                    previousState.copy(
                        status = Status.ERROR
                    )
                }
            }
            is UiEvent.OnDeleteClicked -> {
                val contact = viewState.value?.contact
                return if (contact != null) {
                    deleteContact(contact)
                    previousState.copy(
                        status = Status.PROCESSING
                    )
                } else {
                    previousState.copy(
                        status = Status.ERROR
                    )
                }
            }
            is UiEvent.OnImageUriUpdated -> {
                return previousState.copy(
                    status = Status.CONTENT,
                    contact = previousState.contact.copy(imageUri = event.imageUri)
                )
            }
            is UiEvent.OnNameUpdated -> {
                return previousState.copy(
                    status = Status.CONTENT,
                    contact = previousState.contact.copy(
                        firstName = event.firstName,
                        lastName = event.lastName
                    )
                )
            }
            is UiEvent.SaveImage -> {
                val imageUri = imageSaver.saveImage(event.image)
                imageUri?.let { processUiEvent(UiEvent.OnImageUriUpdated(it.toString())) }
            }
            is DataEvent.ContactSaved -> {
                return previousState.copy(status = Status.FINISHED)
            }
            is DataEvent.ErrorUpdatingContact -> {
                return previousState.copy(status = Status.ERROR)
            }
            is DataEvent.ErrorLoadingContact -> {
                return previousState.copy(status = Status.ERROR)
            }
        }
        return null
    }

    abstract fun saveContact(contact: Contact)

    open fun deleteContact(contact: Contact) {}
}