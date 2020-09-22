package com.example.contacts.editcontactscreen.ui

import com.example.contacts.base.BaseViewModel
import com.example.contacts.base.Event
import com.example.contacts.base.model.Contact

abstract class ContactViewModel() : BaseViewModel<ViewState>() {

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
                saveContact(viewState.value?.contact)
                return previousState.copy(
                    status = Status.PROCESSING,
                    contact = previousState.contact
                )
            }
            is UiEvent.OnDeleteClicked -> {
                deleteContact(viewState.value?.contact)
                return previousState.copy(
                    status = Status.PROCESSING,
                    contact = previousState.contact
                )
            }
            is UiEvent.OnImageUriUpdated -> {
                return previousState.copy(
                    status = Status.CONTENT,
                    contact = previousState.contact.copy(imageUri = event.imageUri)
                )
            }
            is UiEvent.OnFirstNameUpdated -> {
                return previousState.copy(
                    status = Status.CONTENT,
                    contact = previousState.contact.copy(firstName = event.firstName)
                )
            }
            is UiEvent.OnLastNameUpdated -> {
                return previousState.copy(
                    status = Status.CONTENT,
                    contact = previousState.contact.copy(lastName = event.lastName)
                )
            }
        }
        return null
    }

    abstract fun saveContact(contact: Contact?)

    open fun deleteContact(contact: Contact?) {}
}