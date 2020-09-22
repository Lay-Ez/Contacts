package com.example.contacts.editcontactscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contacts.R


class EditContactFragment : Fragment() {


    companion object {

        private const val TAG = "EditContactFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) = EditContactFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupFragment(arguments?.getInt("contact_id"))
        return inflater.inflate(R.layout.fragment_edit_contact, container, false)
    }

    private fun setupFragment(contactId: Int?) {
        if (contactId == null) {
            return
        }
    }
}