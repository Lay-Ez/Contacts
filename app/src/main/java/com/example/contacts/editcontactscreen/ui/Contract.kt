package com.example.contacts.editcontactscreen.ui

import com.example.contacts.base.Event
import com.example.contacts.base.model.Contact

data class ViewState(
    val status: Status,
    val contact: Contact
)

sealed class UiEvent : Event {
    object OnSaveClicked : UiEvent()
    object OnDeleteClicked : UiEvent()
    data class OnFirstNameUpdated(val firstName: String) : UiEvent()
    data class OnLastNameUpdated(val lastName: String) : UiEvent()
    data class OnImageUriUpdated(val imageUri: String) : UiEvent()
}

sealed class DataEvent : Event {
    data class LoadContact(val contactId: Int)
}

enum class Status {
    CONTENT,
    ERROR,
    PROCESSING
}