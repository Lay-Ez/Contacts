package com.example.contacts.editcontactscreen.ui.viewmodel

import com.example.contacts.base.Event
import com.example.contacts.base.model.Contact

data class ViewState(
    val status: Status,
    val contact: Contact
)

sealed class UiEvent : Event {
    object OnSaveClicked : UiEvent()
    object OnDeleteClicked : UiEvent()
    data class OnNameUpdated(val firstName: String, val lastName: String) : UiEvent()
    data class OnImageUriUpdated(val imageUri: String) : UiEvent()
}

sealed class DataEvent : Event {
    data class LoadContact(val contactId: Int) : DataEvent()
    data class ErrorLoadingContact(val error: Throwable) : DataEvent()
    data class ErrorUpdatingContact(val error: Throwable) : DataEvent()
    object ContactSaved : DataEvent()
}

enum class Status {
    CONTENT,
    ERROR,
    PROCESSING,
    FINISHED
}